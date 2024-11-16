package com.coursework.clickboardbackend.category;

import com.coursework.clickboardbackend.category.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    public List<CategoryResponseDto> getAllCategories() {
        return categoryRepository.findAll().stream().map(this::mapToCategoryResponseDto).toList();
    }

    private CategoryResponseDto mapToCategoryResponseDto(Category category) {
        CategoryResponseDto categoryResponseDto = new CategoryResponseDto();
        categoryResponseDto.setId(category.getId());
        categoryResponseDto.setName(category.getName());
        categoryResponseDto.setPhotoUrl(category.getPhotoUrl());
        return categoryResponseDto;
    }
}
