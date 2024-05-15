package com.example.bookRental.service;

import com.example.bookRental.dto.BookRentRequest;
import com.example.bookRental.dto.BookRentalDto;
import com.example.bookRental.model.BookRental;

import java.util.List;

public interface BookRentalService {

    List<BookRentalDto> getAllRentedBooks();

    BookRental getRentedBookById(int id);

    BookRental getRentalByMemberID(int id);

    BookRentalDto getRentedBookByCode(int code);

    BookRentalDto rentBook(BookRentRequest bookRentRequest);

    void returnBook(int code);

}
