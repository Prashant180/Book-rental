package com.example.bookRental.service;

import com.example.bookRental.dto.BookRentRequest;
import com.example.bookRental.dto.BookRentalDto;
import com.example.bookRental.model.BookRental;
import com.example.bookRental.projection.BookRentalProjection;

import java.util.List;

public interface BookRentalService {

    List<BookRentalDto> getAllRentedBooks();

    List<BookRentalProjection> getAllRentedBooksWithName();

    BookRental getRentedBookById(int id);

    BookRental getRentalByMemberID(int id);

    BookRentalDto getRentedBookByCode(int code);

    BookRentalDto rentBook(BookRentRequest bookRentRequest);

    void downloadRentedData();

    void returnBook(int code);

}
