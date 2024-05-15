package com.example.bookRental.mapper;

import com.example.bookRental.dto.CategoryDto;
import com.example.bookRental.model.Category;

public class CategoryMapper {
    public static CategoryDto mapToCategoryDto(Category category){
        CategoryDto categoryDto=new CategoryDto(
                category.getId(),
                category.getName(),
                category.getDescription()
        );
        return categoryDto;
    }

    public static Category mapToCategory(CategoryDto categoryDto){
        Category category=new Category(
                categoryDto.getName(),
                categoryDto.getDescription()
        );
        return category;
    }
}
