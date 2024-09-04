package com.healontrip.component;

import com.healontrip.entity.CategoryEntity;
import com.healontrip.entity.CommunicationEntity;
import com.healontrip.entity.ExperienceYearEntity;
import com.healontrip.entity.SpecialistEntity;
import com.healontrip.repository.CategoryRepository;
import com.healontrip.repository.CommunicationRepository;
import com.healontrip.repository.ExperienceYearRepository;
import com.healontrip.repository.SpecialistRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class DataSeeder implements CommandLineRunner {

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private CommunicationRepository communicationRepository;

    @Autowired
    private ExperienceYearRepository experienceYearRepository;

    @Autowired
    private SpecialistRepository specialistRepository;

    @Value("${seed:false}")
    private boolean seed;

    @Override
    public void run(String... args) throws Exception {
        if (seed) {

            // categories data
            List<CategoryEntity> categoryEntityList = new ArrayList<>();
            categoryEntityList.add(new CategoryEntity("Radiologist"));
            categoryEntityList.add(new CategoryEntity("Internal Medicine"));
            categoryEntityList.add(new CategoryEntity("Pediatrician"));
            categoryEntityList.add(new CategoryEntity("Pediatrics"));
            categoryEntityList.add(new CategoryEntity("General Practitioner"));
            categoryEntityList.add(new CategoryEntity("Dermatologist"));
            categoryEntityList.add(new CategoryEntity("Ophthalmology"));
            categoryEntityList.add(new CategoryEntity("Family Medicine"));
            categoryEntityList.add(new CategoryEntity("Pathologist"));
            categoryEntityList.add(new CategoryEntity("General Surgeon"));

            for (CategoryEntity categoryEntity: categoryEntityList) {
                categoryRepository.save(categoryEntity);
            }


            // communication data
            List<CommunicationEntity> communicationEntityList = new ArrayList<>();
            communicationEntityList.add(new CommunicationEntity("WhatsApp"));
            communicationEntityList.add(new CommunicationEntity("Phone"));
            communicationEntityList.add(new CommunicationEntity("E-Mail"));
            communicationEntityList.add(new CommunicationEntity("SMS"));

            for (CommunicationEntity communicationEntity: communicationEntityList) {
                communicationRepository.save(communicationEntity);
            }


            // experience year data
            List<ExperienceYearEntity> experienceYearEntityList = new ArrayList<>();
            experienceYearEntityList.add(new ExperienceYearEntity("1-5 Years", 1, 5));
            experienceYearEntityList.add(new ExperienceYearEntity("5+ Years", 5, -1));

            for (ExperienceYearEntity experienceYearEntity: experienceYearEntityList) {
                experienceYearRepository.save(experienceYearEntity);
            }


            // specialist data
            List<SpecialistEntity> specialistEntityList = new ArrayList<>();
            specialistEntityList.add(new SpecialistEntity("Allergy/Immunology", "allergy_immunology.png"));
            specialistEntityList.add(new SpecialistEntity("Anesthesiology", "anesthesiology.png"));
            specialistEntityList.add(new SpecialistEntity("Cardiology", "cardiology.png"));
            specialistEntityList.add(new SpecialistEntity("Dermatology", "dermatology.png"));
            specialistEntityList.add(new SpecialistEntity("Endocrinology", "endocrinology.png"));
            specialistEntityList.add(new SpecialistEntity("Family Physician", "family_physician.png"));
            specialistEntityList.add(new SpecialistEntity("Gastroenterology", "gastroenterology.png"));
            specialistEntityList.add(new SpecialistEntity("Genetics", "genetics.png"));
            specialistEntityList.add(new SpecialistEntity("Hematology", "hematology.png"));
            specialistEntityList.add(new SpecialistEntity("Internal Medicine", "internal_medicine.png"));
            specialistEntityList.add(new SpecialistEntity("Nephrology", "nephrology.png"));
            specialistEntityList.add(new SpecialistEntity("Oncology", "oncology.png"));
            specialistEntityList.add(new SpecialistEntity("Ophthalmology", "ophthalmology.png"));
            specialistEntityList.add(new SpecialistEntity("Plastic Surgery", "plastic_surgery.png"));
            specialistEntityList.add(new SpecialistEntity("Surgery", "surgery.png"));

            for (SpecialistEntity specialistEntity: specialistEntityList) {
                specialistRepository.save(specialistEntity);
            }
        }
    }
}