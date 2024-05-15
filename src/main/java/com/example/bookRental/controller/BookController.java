package com.example.bookRental.controller;

import com.example.bookRental.CustomException;
import com.example.bookRental.CustomResponse;
import com.example.bookRental.dto.BookDto;
import com.example.bookRental.dto.BookRequest;
import com.example.bookRental.model.Book;
import com.example.bookRental.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/book")
public class BookController {

    @Autowired
    private BookService service;

    @GetMapping("/all")
    public CustomResponse<List<BookDto>> getBooks() {
        return CustomResponse.success(service.getAllBooks());
    }

    @PostMapping("/add")
    public CustomResponse<BookDto> addBook(@RequestBody BookRequest bookRequest) {
        return CustomResponse.success(service.addBook(bookRequest));
    }

    @GetMapping("/{id}")
    public CustomResponse<BookDto> getBook(@PathVariable Integer id) {
        return CustomResponse.success(service.getBookById(id));
    }

    @DeleteMapping("/{id}")
    public CustomResponse<BookDto> deleteBook(@PathVariable Integer id) {
        service.deleteBook(id);
        return CustomResponse.success("Book deleted with Id "+id);
    }
}
