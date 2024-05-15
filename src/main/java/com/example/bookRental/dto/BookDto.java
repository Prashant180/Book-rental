package com.example.bookRental.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BookDto {
    private Integer id;
    private String bookName;
    private Integer noOfPages;
    private Integer isbn;
    private Integer rating;
    private Integer stock;
    private String photo;
    private LocalDate publishedDate;
    private Integer categoryId;
    private List<Integer> authorIds;
}
