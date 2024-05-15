package com.example.bookRental.dao;

import com.example.bookRental.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoryRepo extends JpaRepository<Category, Integer> {
    List<Category> findByActiveTrue();
    Category findByIdAndActiveTrue(int id);
    Category findByName(String name);
}
