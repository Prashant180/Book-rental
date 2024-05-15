package com.example.bookRental.mapper;

import com.example.bookRental.dto.BookRentalDto;
import com.example.bookRental.model.BookRental;

public class BookRentalMapper {
    public static BookRentalDto mapToBookRentalDto(BookRental bookRental){
        BookRentalDto bookRentalDto=new BookRentalDto(
          bookRental.getId(),
          bookRental.getBook().getId(),
          bookRental.getTransactionCode(),
          bookRental.getFromDate(),
          bookRental.getToDate(),
          bookRental.getStatus(),
          bookRental.getMember().getId()
        );
        return bookRentalDto;
    }

//    public static BookRental mapToBookRental(BookRentalDto bookRentalDto){
//        BookRental bookRental=new BookRental(
//                bookRentalDto.getId(),
//        )
//    }
}
