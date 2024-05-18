package com.example.bookRental.service.impl;

import com.example.bookRental.CustomException;
import com.example.bookRental.dao.CategoryRepo;
import com.example.bookRental.dto.CategoryDto;
import com.example.bookRental.mapper.CategoryMapper;
import com.example.bookRental.model.Category;
import com.example.bookRental.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryRepo categoryRepo;

    @Override
    public List<CategoryDto> getAllCategory() {
        List<Category> categories = categoryRepo.findByActiveTrue();
        return categories.stream().map(CategoryMapper::mapToCategoryDto).collect(Collectors.toList());
    }

    @Override
    public CategoryDto addCategory(CategoryDto categoryDto) {
        if (!categoryDto.getName().matches("^[a-zA-Z\s]+$")) {
            throw new CustomException(HttpStatus.NOT_ACCEPTABLE, "Invalid name format!");
        }
        Category category = categoryRepo.findByName(categoryDto.getName().trim());
        if (category != null) {
            throw new CustomException(HttpStatus.BAD_REQUEST, "Category with this name already exist!");
        }
        Category savedCategory = categoryRepo.save(CategoryMapper.mapToCategory(categoryDto));
        return CategoryMapper.mapToCategoryDto(savedCategory);
    }

    @Override
    public CategoryDto getCategoryById(Integer id) {
        Category category = categoryRepo.findByIdAndActiveTrue(id);
        if (category == null) {
            throw new CustomException(HttpStatus.NOT_FOUND, "Invalid Category Id!");
        }
        return CategoryMapper.mapToCategoryDto(category);
    }

    @Override
    public void deleteCategory(Integer id) {
        Optional<Category> category = categoryRepo.findById(id);
        if (category.isEmpty()) {
            throw new CustomException(HttpStatus.BAD_REQUEST, "Invalid category Id");
        }
        if (category.get().getActive()) {
            category.get().setActive(false);
        } else {
            throw new CustomException(HttpStatus.BAD_REQUEST, "Category already deleted with Id " + id);
        }
        categoryRepo.save(category.get());

    }
}
