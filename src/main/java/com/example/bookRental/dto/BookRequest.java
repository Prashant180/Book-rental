package com.example.bookRental.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@Setter
@Getter
public class BookRequest {
    private String bookName;
    private Integer noOfPages;
    private Integer isbn;
    private Integer rating;
    private Integer stock;
    private String photo;
    private LocalDate publishedDate;
    private Integer categoryId;
    private List<Integer> authorsId;
}
