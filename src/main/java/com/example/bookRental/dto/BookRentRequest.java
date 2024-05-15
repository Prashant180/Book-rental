package com.example.bookRental.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class BookRentRequest {
    private Integer bookId;
    private Integer memberId;
    private Integer days;
}
