package com.example.bookRental.controller;

import com.example.bookRental.CustomResponse;
import com.example.bookRental.dto.CategoryDto;
import com.example.bookRental.model.Author;
import com.example.bookRental.model.Category;
import com.example.bookRental.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/category")
public class CategoryController {

    @Autowired
    private CategoryService service;

    @GetMapping("/all")
    public CustomResponse<List<CategoryDto>> getCategories(){
        return CustomResponse.success(service.getAllCategory());
    }

    @PostMapping("/add")
    public CustomResponse<CategoryDto> addCategory(@RequestBody CategoryDto categoryDto){
        return  CustomResponse.success(service.addCategory(categoryDto));
    }

    @GetMapping("/{id}")
    public CustomResponse<CategoryDto> getCategory(@PathVariable Integer id){
        return CustomResponse.success(service.getCategoryById(id));
    }

    @DeleteMapping("/{id}")
    public CustomResponse<CategoryDto> deleteCategory(@PathVariable Integer id){
        service.deleteCategory(id);
        return CustomResponse.success("Category deleted with Id "+id);
    }

}
