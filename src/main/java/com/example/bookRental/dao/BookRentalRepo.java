package com.example.bookRental.dao;

import com.example.bookRental.model.BookRental;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface BookRentalRepo extends JpaRepository<BookRental, Integer> {
    BookRental findFirstByMemberIdOrderByIdDesc(Integer id);
    BookRental findByTransactionCode(Integer code);
}
