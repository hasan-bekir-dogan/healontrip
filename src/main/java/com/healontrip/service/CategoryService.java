package com.healontrip.service;

import com.healontrip.dto.CategoryByCountDto;
import com.healontrip.dto.CategoryDto;
import com.healontrip.entity.CategoryEntity;

import java.util.List;

public interface CategoryService {
    List<CategoryDto> getAllCategories();
    List<CategoryByCountDto> getBlogCategories();
    CategoryEntity findById(Long id);
    CategoryDto EntitytoDto(CategoryEntity categoryEntity);
    CategoryEntity DtoToEntity(CategoryDto categoryDto);
}
