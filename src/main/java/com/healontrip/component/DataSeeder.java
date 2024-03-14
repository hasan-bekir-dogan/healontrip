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
            categoryEntityList.add(new CategoryEntity("Internal medicine"));
            categoryEntityList.add(new CategoryEntity("Pediatrician"));
            categoryEntityList.add(new CategoryEntity("Pediatrics"));
            categoryEntityList.add(new CategoryEntity("General practitioner"));
            categoryEntityList.add(new CategoryEntity("Dermatologist"));
            categoryEntityList.add(new CategoryEntity("Ophthalmology"));
            categoryEntityList.add(new CategoryEntity("Family medicine"));
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
            specialistEntityList.add(new SpecialistEntity("Allergist/immunologist"));
            specialistEntityList.add(new SpecialistEntity("Anesthesiologist"));
            specialistEntityList.add(new SpecialistEntity("Cardiologist"));
            specialistEntityList.add(new SpecialistEntity("Dermatologist"));
            specialistEntityList.add(new SpecialistEntity("Endocrinologist"));
            specialistEntityList.add(new SpecialistEntity("Family physician"));
            specialistEntityList.add(new SpecialistEntity("Gastroenterologist"));
            specialistEntityList.add(new SpecialistEntity("Geneticist"));
            specialistEntityList.add(new SpecialistEntity("Hematologist"));
            specialistEntityList.add(new SpecialistEntity("Hospice and palliative medicine specialist"));
            specialistEntityList.add(new SpecialistEntity("Infectious disease physician"));
            specialistEntityList.add(new SpecialistEntity("Internal Medicine"));
            specialistEntityList.add(new SpecialistEntity("Nephrologist"));
            specialistEntityList.add(new SpecialistEntity("Neurologist"));
            specialistEntityList.add(new SpecialistEntity("Obstetrician/gynecologist (OBGYNs)"));
            specialistEntityList.add(new SpecialistEntity("Oncologist"));
            specialistEntityList.add(new SpecialistEntity("Ophthalmologist"));
            specialistEntityList.add(new SpecialistEntity("Orthopedist"));
            specialistEntityList.add(new SpecialistEntity("Otolaryngologist"));
            specialistEntityList.add(new SpecialistEntity("Osteopath"));
            specialistEntityList.add(new SpecialistEntity("Pathologist"));
            specialistEntityList.add(new SpecialistEntity("Pediatrician"));
            specialistEntityList.add(new SpecialistEntity("Physician executive"));
            specialistEntityList.add(new SpecialistEntity("Plastic surgeon"));
            specialistEntityList.add(new SpecialistEntity("Podiatrist"));
            specialistEntityList.add(new SpecialistEntity("Psychiatrist"));
            specialistEntityList.add(new SpecialistEntity("Pulmonologist"));
            specialistEntityList.add(new SpecialistEntity("Radiologist"));
            specialistEntityList.add(new SpecialistEntity("Rheumatologist "));
            specialistEntityList.add(new SpecialistEntity("Sleep medicine specialist "));
            specialistEntityList.add(new SpecialistEntity("Surgeon"));
            specialistEntityList.add(new SpecialistEntity("Urologist"));

            for (SpecialistEntity specialistEntity: specialistEntityList) {
                specialistRepository.save(specialistEntity);
            }
        }
    }
}