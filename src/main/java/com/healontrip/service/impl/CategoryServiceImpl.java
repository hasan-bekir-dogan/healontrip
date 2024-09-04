package com.healontrip.service.impl;

import com.healontrip.dto.CategoryByCountDto;
import com.healontrip.dto.CategoryDto;
import com.healontrip.entity.CategoryEntity;
import com.healontrip.exception.ResourceNotFoundException;
import com.healontrip.repository.CategoryRepository;
import com.healontrip.service.CategoryService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {
    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public List<CategoryDto> getAllCategories() {
        List<CategoryEntity> categoryEntityList = categoryRepository.findAll();
        List<CategoryDto> categoryDtoList = new ArrayList<>();

        for (CategoryEntity entity: categoryEntityList){
            CategoryDto categoryDto = EntitytoDto(entity);
            categoryDtoList.add(categoryDto);
        }

        return categoryDtoList;
    }

    @Override
    public List<CategoryByCountDto> getBlogCategories() {
        List<CategoryByCountDto> categoryByCountDtoList = categoryRepository.findAllByCount();

        return categoryByCountDtoList;
    }

    @Override
    public CategoryEntity findById(Long id) {
        return categoryRepository.findById(id).orElseThrow(() ->
                new ResourceNotFoundException("Category not found with id: " + id));
    }

    ////////////////////////////////////////////////////////////////////////////////////////
    // Model Mapper
    // Entity => DTO@Override
    @Override
    public CategoryDto EntitytoDto(CategoryEntity categoryEntity) {
        return modelMapper.map(categoryEntity, CategoryDto.class);
    }

    // DTO => Entity
    @Override
    public CategoryEntity DtoToEntity(CategoryDto categoryDto) {
        return modelMapper.map(categoryDto, CategoryEntity.class);
    }
}
