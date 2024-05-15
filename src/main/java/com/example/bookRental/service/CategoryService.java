package com.example.bookRental.service;

import com.example.bookRental.dto.CategoryDto;
import com.example.bookRental.model.Category;

import java.util.List;

public interface CategoryService {

    List<CategoryDto> getAllCategory();

    CategoryDto addCategory(CategoryDto categoryDto);

    CategoryDto getCategoryById(Integer id);

    void deleteCategory(Integer id);
}
