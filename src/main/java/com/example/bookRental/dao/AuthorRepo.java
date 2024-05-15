package com.example.bookRental.dao;

import com.example.bookRental.model.Author;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AuthorRepo extends JpaRepository<Author, Integer> {
    Author findByEmail(String email);
    Author findByMobileNumber(String mobileNumber);
    List<Author> findByActiveTrue();
    Author findByIdAndActiveTrue(int id);

}
