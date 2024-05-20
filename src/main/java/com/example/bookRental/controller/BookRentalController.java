package com.example.bookRental.controller;

import com.example.bookRental.CustomException;
import com.example.bookRental.CustomResponse;
import com.example.bookRental.dto.BookRentRequest;
import com.example.bookRental.dto.BookRentalDto;
import com.example.bookRental.model.BookRental;
import com.example.bookRental.projection.BookRentalProjection;
import com.example.bookRental.service.BookRentalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/rented-book")
public class BookRentalController {

    @Autowired
    private BookRentalService service;

    @GetMapping("/all")
    public CustomResponse<List<BookRentalDto>> getAllRentedBooks() {
        return CustomResponse.success(service.getAllRentedBooks());
    }

    @GetMapping("/all-with-name")
    public CustomResponse<List<BookRentalProjection>> getAllRentedBookWithName(){
        return CustomResponse.success(service.getAllRentedBooksWithName());
    }

    @GetMapping("/download")
    public CustomResponse<BookRentalDto> downloadRentedData(){
        service.downloadRentedData();
        return CustomResponse.success();
    }

    @GetMapping("/by-code/{code}")
    public CustomResponse<BookRentalProjection> getRentedBookByCode(@PathVariable Integer code) {
        return CustomResponse.success(service.getRentedBookByCode(code));
    }

    @PostMapping("/add")
    public CustomResponse<BookRentalDto> addRentedBook(@RequestBody BookRentRequest bookRentRequest) {
            return CustomResponse.success(service.rentBook(bookRentRequest));
    }

    @PutMapping("/return/{code}")
    public CustomResponse<BookRentalDto> returnBook(@PathVariable Integer code) {
        service.returnBook(code);
        return CustomResponse.success();
    }
}
