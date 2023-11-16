package com.healontrip.component;

import com.healontrip.entity.CategoryEntity;
import com.healontrip.entity.ExperienceYearEntity;
import com.healontrip.repository.CategoryRepository;
import com.healontrip.repository.ExperienceYearRepository;
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
    private ExperienceYearRepository experienceYearRepository;

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

            // categories data
            List<ExperienceYearEntity> experienceYearEntityList = new ArrayList<>();
            experienceYearEntityList.add(new ExperienceYearEntity("1-5 Years", 1, 5));
            experienceYearEntityList.add(new ExperienceYearEntity("5+ Years", 5, -1));

            for (ExperienceYearEntity experienceYearEntity: experienceYearEntityList) {
                experienceYearRepository.save(experienceYearEntity);
            }

        }
    }
}
