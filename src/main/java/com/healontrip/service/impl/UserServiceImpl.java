package com.healontrip.service.impl;

import com.healontrip.dto.*;
import com.healontrip.entity.EducationEntity;
import com.healontrip.entity.FileEntity;
import com.healontrip.entity.SpecialistEntity;
import com.healontrip.entity.UserEntity;
import com.healontrip.exception.ResourceNotFoundException;
import com.healontrip.repository.EducationRepository;
import com.healontrip.repository.FileRepository;
import com.healontrip.repository.SpecialistRepository;
import com.healontrip.repository.UserRepository;
import com.healontrip.service.*;
import org.apache.commons.lang3.StringUtils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;


@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private FileRepository fileRepository;

    @Autowired
    private FileService fileService;

    @Autowired
    private ServiceService serviceService;

    @Autowired
    private SpecialistRepository specialistRepository;

    @Autowired
    private SpecialistService specialistService;

    @Autowired
    private EducationRepository educationRepository;

    @Autowired
    private EducationService educationService;

    @Autowired
    private ExperienceService experienceService;

    @Autowired
    private AwardService awardService;

    @Autowired
    private MembershipService membershipService;

    @Autowired
    private AuthService authService;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    HttpSession session;

    public final String dateOfBirthPattern = "yyyy-MM-dd";
    public final String dateOfBirthPatternBar = "dd MMM yyyy";

    @Override
    public void saveUser(UserDto userDto){
        UserEntity userEntity = new UserEntity();
        userEntity.setName(userDto.getName());
        userEntity.setEmail(userDto.getEmail());
        userEntity.setRole(userDto.getRole());

        userEntity.setPassword(passwordEncoder.encode(userDto.getPassword()));

        // Save Default Profile Img
        String alternateText = "Profile Image";

        FileEntity fileEntity = fileService.findByCode(String.valueOf(FileCode.PROFILE_IMG));

        if (fileEntity == null) {
            fileEntity = new FileEntity();
            fileEntity.setAlt(alternateText);
            fileEntity.setCode(FileCode.PROFILE_IMG);
            fileEntity.setSource(FileSource.ASSETS_GENERAL_IMG.getSrc());
            fileEntity.setName(FileCode.PROFILE_IMG.getCode());
            fileEntity.setExtension(FileExtension.JPG.getExt());

            fileRepository.save(fileEntity);
        }

        userEntity.setProfileImgId(fileEntity.getId());

        userRepository.save(userEntity);
    }

    @Override
    public void updateProfile(ProfileDto profileDto) throws IOException, ParseException {
        UserEntity userEntity = findByEmail(profileDto.getEmail());
        Long userId = userEntity.getId();

        userEntity.setName(profileDto.getName());
        userEntity.setPhoneNumber(profileDto.getPhone());
        userEntity.setGender(profileDto.getGender());

        // date of birth
        SimpleDateFormat format = new SimpleDateFormat(dateOfBirthPattern);
        Date dateOfBirth = null;

        if (profileDto.getDateOfBirth() != null)
            dateOfBirth = format.parse(profileDto.getDateOfBirth());

        userEntity.setDateOfBirth(dateOfBirth);

        // update profile photo
        if (!profileDto.getImage().isEmpty()) {
            FileEntity fileEntityOld = fileService.findById(userEntity.getProfileImgId());
            FileDto fileDtoOld = FileEntityToFileDto(fileEntityOld);

            FileDto fileDtoNew = new FileDto();

            String alternateText = "Profile Image";
            fileDtoNew.setFile(profileDto.getImage());
            fileDtoNew.setAlt(alternateText);
            fileDtoNew.setSource(FileSource.UPLOADS.getSrc());
            fileDtoNew.setExtension(FileExtension.JPG.getExt());

            Long fileId = fileService.updateFile(fileDtoOld, fileDtoNew);

            userEntity.setProfileImgId(fileId);
        }

        // biography
            userEntity.setBiography(profileDto.getBiography());

        // clinic
        userEntity.setClinicName(profileDto.getClinicName());
        userEntity.setClinicAddress(profileDto.getClinicAddress());

        // delete the removed clinic images
        if (profileDto.getDeletedClinicImageList().get(0) != -1) {
            for (Long imageId : profileDto.getDeletedClinicImageList()) {
                // delete from clinic images column of user table on db
                String currentClinicImgIds = userEntity.getClinicImgIds();
                String newClinicImgIds = currentClinicImgIds.replace((imageId + ","), "");
                newClinicImgIds = newClinicImgIds.replace(("," + imageId), "");
                newClinicImgIds = newClinicImgIds.replace(imageId + "", "");

                userEntity.setClinicImgIds(newClinicImgIds);

                // delete from file table and folder
                FileEntity fileEntity = fileService.findById(imageId);

                FileDto fileDto = FileEntitytoFileDto(fileEntity);

                fileService.deleteFile(fileDto);
            }
        }

        // save clinic images
        if (!profileDto.getClinicImages()[0].isEmpty()) {
            for (int i = 0; i < profileDto.getClinicImages().length; i++) {
                if (profileDto.getClinicImages()[i] == null)
                    continue;

                // Save file
                String alternateText = "Clinic Image";

                FileDto fileDto = new FileDto();

                fileDto.setFile(profileDto.getClinicImages()[i]);
                fileDto.setAlt(alternateText);
                fileDto.setSource(FileSource.UPLOADS.getSrc());
                fileDto.setExtension(FileExtension.JPG.getExt());

                Long fileId = fileService.saveFile(fileDto);

                if (userEntity.getClinicImgIds() == "" || userEntity.getClinicImgIds() == null)
                    userEntity.setClinicImgIds(String.valueOf(fileId));
                else
                    userEntity.setClinicImgIds(userEntity.getClinicImgIds() + "," + fileId);
            }
        }

        // contact details
        userEntity.setCity(profileDto.getCity());
        userEntity.setState(profileDto.getState());
        userEntity.setCountry(profileDto.getCountry());
        userEntity.setPostalCode(profileDto.getPostalCode());
        userEntity.setAddressLine(profileDto.getAddressLine());

        // update service and specialist
        profileDto.setId(userId);

        serviceService.updateService(profileDto);
        specialistService.updateSpecialist(profileDto);

        // delete the removed educations
        if (profileDto.getDeletedEducationList().get(0) != -1) {
            for (Long educationId: profileDto.getDeletedEducationList()) {
                educationService.deleteEducation(educationId);
            }
        }

        // save educations
        if (profileDto.getEducationList() != null) {
            for (EducationDto educationDto: profileDto.getEducationList()) {
                educationDto.setUserId(userId);

                if (educationDto.getId() == -1) // create education
                    educationService.createEducation(educationDto);
                else if (educationDto.getId() != -1) // update education
                    educationService.updateEducation(educationDto);
            }
        }

        // delete the removed experiences
        if (profileDto.getDeletedExperienceList().get(0) != -1) {
            for (Long experienceId: profileDto.getDeletedExperienceList()) {
                experienceService.deleteExperience(experienceId);
            }
        }

        // save experiences
        if (profileDto.getExperienceList() != null) {
            for (ExperienceDto experienceDto: profileDto.getExperienceList()) {
                experienceDto.setUserId(userId);

                if (experienceDto.getId() == -1) // create experience
                    experienceService.createExperience(experienceDto);
                else if (experienceDto.getId() != -1) // update experience
                    experienceService.updateExperience(experienceDto);
            }
        }

        // delete the removed awards
        if (profileDto.getDeletedAwardList().get(0) != -1) {
            for (Long awardId: profileDto.getDeletedAwardList()) {
                awardService.deleteAward(awardId);
            }
        }

        // save awards
        if (profileDto.getAwardList() != null) {
            for (AwardDto awardDto: profileDto.getAwardList()) {
                awardDto.setUserId(userId);

                if (awardDto.getId() == -1) // create award
                    awardService.createAward(awardDto);
                else if (awardDto.getId() != -1) // update award
                    awardService.updateAward(awardDto);
            }
        }

        // delete the removed memberships
        if (profileDto.getDeletedMembershipList().get(0) != -1) {
            for (Long membershipId: profileDto.getDeletedMembershipList()) {
                membershipService.deleteMembership(membershipId);
            }
        }

        // save memberships
        if (profileDto.getMembershipList() != null) {
            for (MembershipDto membershipDto: profileDto.getMembershipList()) {
                membershipDto.setUserId(userId);

                if (membershipDto.getId() == -1) // create membership
                    membershipService.createMembership(membershipDto);
                else if (membershipDto.getId() != -1) // update membership
                    membershipService.updateMembership(membershipDto);
            }
        }

        // save the user
        userRepository.save(userEntity);
    }

    @Override
    public void updatePatientProfile(ProfileDto profileDto) throws IOException, ParseException {
        UserEntity userEntity = findByEmail(profileDto.getEmail());
        Long userId = userEntity.getId();

        userEntity.setName(profileDto.getName());
        userEntity.setPhoneNumber(profileDto.getPhone());
        userEntity.setGender(profileDto.getGender());

        // date of birth
        SimpleDateFormat format = new SimpleDateFormat(dateOfBirthPattern);
        Date dateOfBirth = null;

        if (profileDto.getDateOfBirth() != null)
            dateOfBirth = format.parse(profileDto.getDateOfBirth());

        userEntity.setDateOfBirth(dateOfBirth);

        // contact details
        userEntity.setCity(profileDto.getCity());
        userEntity.setState(profileDto.getState());
        userEntity.setCountry(profileDto.getCountry());
        userEntity.setPostalCode(profileDto.getPostalCode());
        userEntity.setAddressLine(profileDto.getAddressLine());

        // update service and specialist
        profileDto.setId(userId);

        // save the user
        userRepository.save(userEntity);
    }

    @Override
    public UserBarDto getUser() throws ParseException {
        UserBarDto userBarDto = new UserBarDto();

        if(!authService.isAuthenticated()) { // Not Authenticated
            return userBarDto;
        }

        Long userId = authService.getUserId();
        UserEntity userEntity = findById(userId);
        String userRole = authService.getRole();

        // role
        userBarDto.setRole(userRole);
        userBarDto.setRoleInitCap(StringUtils.capitalize(userRole.toLowerCase()));

        // user name
        String extendedName = ((userRole.equals("DOCTOR")) ? RolePrefix.DOCTOR.getPre() : "") + userEntity.getName();
        userBarDto.setUserName(extendedName);

        // profile photo src
        String imgSrc = fileService.getFileSrc(userEntity.getProfileImgId());
        userBarDto.setProfileImgSrc(imgSrc);

        // profile photo alt
        FileEntity fileEntity = fileService.findById(userEntity.getProfileImgId());
        userBarDto.setProfileImgAlt(fileEntity.getAlt());

        // patient
        if(userRole.equals(Role.PATIENT.toString())) {
            // address
            String addressShort = userEntity.getCity() + ", " + userEntity.getCountry();
            userBarDto.setAddressShort(addressShort);

            // date of birth
            if (userEntity.getDateOfBirth() != null) {
                SimpleDateFormat format = new SimpleDateFormat(dateOfBirthPattern);
                Date currentDate = new Date();
                String dateOfBirth;

                Date d1 = format.parse(format.format(userEntity.getDateOfBirth()));
                Date d2 = format.parse(format.format(currentDate));

                long difference_In_Time = d2.getTime() - d1.getTime();
                long difference_In_Years = (difference_In_Time / (1000l * 60 * 60 * 24 * 365));

                SimpleDateFormat formatBar = new SimpleDateFormat(dateOfBirthPatternBar);

                dateOfBirth = formatBar.format(userEntity.getDateOfBirth()) + ", " + difference_In_Years + " years";

                userBarDto.setDateOfBirth(dateOfBirth);
            }
        }

        // doctor
        if(userRole.equals(Role.DOCTOR.toString())) {
            // short information
            List<EducationEntity> educationEntityList = educationRepository.findEducationByUserIdLimit2(userId);
            String educationDegrees = "";

            for(EducationEntity educationEntity: educationEntityList)
                educationDegrees += ((educationDegrees != "") ? ", " : "") + educationEntity.getDegree();

            SpecialistEntity specialistEntity = specialistRepository.findSpecialistByUserIdLimit1(userId);
            String specialistName = specialistEntity.getName();

            String infoShort = educationDegrees + ((educationDegrees != "" && specialistName != "") ? " - " : "") + specialistName;

            userBarDto.setInfoShort(infoShort);
        }

        return userBarDto;
    }

    @Override
    public ProfileDto getProfile() throws ParseException {
        ProfileDto profileDto = new ProfileDto();

        if(!authService.isAuthenticated()) { // Not Authenticated
            return profileDto;
        }

        Long userId = authService.getUserId();
        UserEntity userEntity = findById(userId);
        String userRole = authService.getRole();

        profileDto.setRole(StringUtils.capitalize(userRole.toLowerCase()));

        profileDto.setName(userEntity.getName());
        profileDto.setEmail(userEntity.getEmail());
        profileDto.setPhone(userEntity.getPhoneNumber());
        profileDto.setGender(userEntity.getGender());

        // date of birth
        SimpleDateFormat format = new SimpleDateFormat(dateOfBirthPattern);
        String dateOfBirth = null;

        if (userEntity.getDateOfBirth() != null) {
            dateOfBirth = format.format(userEntity.getDateOfBirth());
        }

        profileDto.setDateOfBirth(dateOfBirth);

        // img src
        String imgSrc = fileService.getFileSrc(userEntity.getProfileImgId());
        profileDto.setProfileImgSrc(imgSrc);

        // img alt
        FileEntity fileEntity = fileService.findById(userEntity.getProfileImgId());
        profileDto.setProfileImgAlt(fileEntity.getAlt());

        // biography
        profileDto.setBiography(userEntity.getBiography());

        // clinic
        profileDto.setClinicName(userEntity.getClinicName());
        profileDto.setClinicAddress(userEntity.getClinicAddress());

        // clinic images
        if (userEntity.getClinicImgIds() != null && !userEntity.getClinicImgIds().equals("")) {
            String[] clinicImageIds = userEntity.getClinicImgIds().split(",");
            List<ImgDto> clinicImageList = new ArrayList<>();

            for (String imageId : clinicImageIds) {
                ImgDto imgDto = new ImgDto();

                // img src
                String clinicImgSrc = fileService.getFileSrc(Long.valueOf(imageId));
                imgDto.setImgSrc(clinicImgSrc);

                // img alt
                fileEntity = fileService.findById(Long.valueOf(imageId));
                imgDto.setImgAlt(fileEntity.getAlt());

                // img id
                imgDto.setId(fileEntity.getId());

                clinicImageList.add(imgDto);
            }
            profileDto.setClinicImageList(clinicImageList);
        }

        // contact details
        profileDto.setCity(userEntity.getCity());
        profileDto.setState(userEntity.getState());
        profileDto.setCountry(userEntity.getCountry());
        profileDto.setPostalCode(userEntity.getPostalCode());
        profileDto.setAddressLine(userEntity.getAddressLine());

        // service
        String serviceList = serviceService.getServices(userId);
        profileDto.setService(serviceList);

        // specialist
        String specialistList = specialistService.getSpecialists(userId);
        profileDto.setSpecialist(specialistList);

        // education
        List<EducationDto> educationList = educationService.getEducationList(userId);
        profileDto.setEducationList(educationList);

        // experience
        List<ExperienceDto> experienceList = experienceService.getExperienceList(userId);
        profileDto.setExperienceList(experienceList);

        // award
        List<AwardDto> awardList = awardService.getAwardList(userId);
        profileDto.setAwardList(awardList);

        // membership
        List<MembershipDto> membershipList = membershipService.getMembershipList(userId);
        profileDto.setMembershipList(membershipList);

        return profileDto;
    }

    @Override
    public UserEntity findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public UserEntity findById(Long id) {
        return userRepository.findById(id).orElseThrow(() ->
                new ResourceNotFoundException("User not found with id: " + id));
    }

    @Override
    public List<DoctorsDto> getDoctors() {
        List<UserEntity> userEntityList = userRepository.findUsersByRole(String.valueOf(Role.DOCTOR));
        List<DoctorsDto> doctorsDtoList = new ArrayList<>();

        for(UserEntity userEntity: userEntityList) {
            DoctorsDto doctorsDto = new DoctorsDto();

            doctorsDto.setUserId(userEntity.getId());

            String userRole = String.valueOf(Role.DOCTOR);
            String extendedName = ((userRole.equals("DOCTOR")) ? RolePrefix.DOCTOR.getPre() : "") + userEntity.getName();
            doctorsDto.setUserName(extendedName);

            // User image (begin)
            FileEntity fileEntity = fileService.findById(userEntity.getProfileImgId());
            String imgSrc = fileService.getFileSrc(fileEntity.getId());
            // User image (end)

            doctorsDto.setUserImgSrc(imgSrc);
            doctorsDto.setUserImgAlt(fileEntity.getAlt());

            doctorsDtoList.add(doctorsDto);
        }

        return doctorsDtoList;
    }

    @Override
    public List<DoctorsDto> getDoctors(SearchFilterDto searchFilterDto) {
        // check null
        boolean genderNullCheck = false, specialistNullCheck = false, experienceYearNullCheck = false;

        if (searchFilterDto.getGenderList() == null || searchFilterDto.getGenderList().isEmpty())
            genderNullCheck = true;
        if (searchFilterDto.getSpecialityList() == null || searchFilterDto.getSpecialityList().isEmpty())
            specialistNullCheck = true;
        if (searchFilterDto.getExperienceYearList() == null || searchFilterDto.getExperienceYearList().isEmpty())
            experienceYearNullCheck = true;

        // upper gender list
        List<String> upperGenderList = new ArrayList<>();

        if (searchFilterDto.getGenderList() != null && !searchFilterDto.getGenderList().isEmpty()) {
            for (String gender : searchFilterDto.getGenderList())
                if (gender.equals(GenderCode.MALE.getId()))
                    upperGenderList.add(GenderCode.MALE.getName().toUpperCase());
                else if (gender.equals(GenderCode.FEMALE.getId()))
                    upperGenderList.add(GenderCode.FEMALE.getName().toUpperCase());
        }

        // get specialist name
        List<String> specialityNameList = new ArrayList<>();

        if (searchFilterDto.getSpecialityList() != null && !searchFilterDto.getSpecialityList().isEmpty()) {
            for (Long id : searchFilterDto.getSpecialityList()) {
                SpecialistEntity specialistEntity = specialistService.findById(id);
                specialityNameList.add(specialistEntity.getName().toUpperCase());
            }
        }

        // get filtered list
        List<UserEntity> userEntityList = new ArrayList<>();

        if(searchFilterDto.getExperienceYearList() != null && !searchFilterDto.getExperienceYearList().equals("")) {
            for (int experienceYearId : searchFilterDto.getExperienceYearList()) {
                List<UserEntity> tempUserEntityList = userRepository.findDoctorsByFilter(
                        String.valueOf(Role.DOCTOR),
                        upperGenderList,
                        genderNullCheck,
                        specialityNameList,
                        specialistNullCheck,
                        experienceYearId,
                        experienceYearNullCheck
                );

                for (UserEntity userEntity : tempUserEntityList) {
                    userEntityList.add(userEntity);
                }
            }
        }
        else {
            userEntityList = userRepository.findDoctorsByFilter(
                    String.valueOf(Role.DOCTOR),
                    upperGenderList,
                    genderNullCheck,
                    specialityNameList,
                    specialistNullCheck,
                    -1,
                    experienceYearNullCheck
            );
        }

        List<DoctorsDto> doctorsDtoList = new ArrayList<>();

        for(UserEntity userEntity: userEntityList) {
            DoctorsDto doctorsDto = new DoctorsDto();

            doctorsDto.setUserId(userEntity.getId());

            String userRole = String.valueOf(Role.DOCTOR);
            String extendedName = ((userRole.equals("DOCTOR")) ? RolePrefix.DOCTOR.getPre() : "") + userEntity.getName();
            doctorsDto.setUserName(extendedName);

            // User image (begin)
            FileEntity fileEntity = fileService.findById(userEntity.getProfileImgId());
            String imgSrc = fileService.getFileSrc(fileEntity.getId());
            // User image (end)

            doctorsDto.setUserImgSrc(imgSrc);
            doctorsDto.setUserImgAlt(fileEntity.getAlt());

            doctorsDtoList.add(doctorsDto);
        }

        return doctorsDtoList;
    }

    @Override
    public DoctorDto getDoctor(Long id) {
        UserEntity userEntity = findById(id);
        DoctorDto doctorDto = new DoctorDto();

        doctorDto.setUserId(id);

        String extendedName = RolePrefix.DOCTOR.getPre() + userEntity.getName();
        doctorDto.setUserName(extendedName);

        // User image (begin)
        FileEntity fileEntity = fileService.findById(userEntity.getProfileImgId());
        String imgSrc = fileService.getFileSrc(fileEntity.getId());

        doctorDto.setUserImgSrc(imgSrc);
        doctorDto.setUserImgAlt(fileEntity.getAlt());
        // User image (end)

        // biography
        doctorDto.setBiography(userEntity.getBiography());

        // clinic images
        if (userEntity.getClinicImgIds() != null && !userEntity.getClinicImgIds().equals("")) {
            String[] clinicImageIds = userEntity.getClinicImgIds().split(",");
            List<ImgDto> clinicImageList = new ArrayList<>();

            for (String imageId : clinicImageIds) {
                ImgDto imgDto = new ImgDto();

                // img src
                String clinicImgSrc = fileService.getFileSrc(Long.valueOf(imageId));
                imgDto.setImgSrc(clinicImgSrc);

                // img alt
                fileEntity = fileService.findById(Long.valueOf(imageId));
                imgDto.setImgAlt(fileEntity.getAlt());

                // img id
                imgDto.setId(fileEntity.getId());

                clinicImageList.add(imgDto);
            }
            doctorDto.setClinicImageList(clinicImageList);
        }

        // service
        List<ServiceDto> serviceList = serviceService.getServiceList(id);
        doctorDto.setServiceList(serviceList);

        // specialist
        List<SpecializationDto> specializationList = specialistService.getSpecialistList(id);
        doctorDto.setSpecialistList(specializationList);

        // education
        List<EducationDto> educationList = educationService.getEducationList(id);
        doctorDto.setEducationList(educationList);

        // experience
        List<ExperienceDto> experienceList = experienceService.getExperienceList(id);
        doctorDto.setExperienceList(experienceList);

        // award
        List<AwardDto> awardList = awardService.getAwardList(id);
        doctorDto.setAwardList(awardList);

        // membership
        List<MembershipDto> membershipList = membershipService.getMembershipList(id);
        doctorDto.setMembershipList(membershipList);

        return doctorDto;
    }


    ////////////////////////////////////////////////////////////////////////////////////////
    // Model Mapper
    // Entity => DTO
    @Override
    public UserBarDto UserEntityToUserBarDto(UserEntity userEntity) {
        UserBarDto userBarDto = modelMapper.map(userEntity, UserBarDto.class);
        return userBarDto;
    }
    @Override
    public ProfileDto UserEntityToProfileDto(UserEntity userEntity) {
        ProfileDto profileDto = modelMapper.map(userEntity, ProfileDto.class);
        return profileDto;
    }
    @Override
    public FileDto FileEntityToFileDto(FileEntity fileEntity) {
        FileDto fileDto = modelMapper.map(fileEntity, FileDto.class);
        return fileDto;
    }
    @Override
    public FileDto FileEntitytoFileDto(FileEntity fileEntity) {
        return modelMapper.map(fileEntity, FileDto.class);
    }

    // DTO => Entity
    @Override
    public UserEntity UserBarDtoToUserEntity(UserBarDto userBarDto) {
        UserEntity userEntity = modelMapper.map(userBarDto, UserEntity.class);
        return userEntity;
    }
    @Override
    public UserEntity ProfileDtoToUserEntity(ProfileDto profileDto) {
        UserEntity userEntity = modelMapper.map(profileDto, UserEntity.class);
        return userEntity;
    }
}
