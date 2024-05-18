package com.example.bookRental.dao;

import com.example.bookRental.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookRepo extends JpaRepository<Book, Integer> {
    List<Book> findByActiveTrue();
    Book findByIdAndActiveTrue(int id);
    Book findByBookName(String  name);
}
