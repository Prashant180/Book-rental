package com.example.bookRental.service;

import com.example.bookRental.dto.BookDto;
import com.example.bookRental.dto.BookRequest;
import com.example.bookRental.model.Book;

import java.util.List;

public interface BookService {


    List<BookDto> getAllBooks();

    BookDto getBookById(Integer id);

    BookDto addBook(BookRequest bookRequest);

    void deleteBook(Integer id);

    BookDto updateBook(BookDto bookDto);
}
