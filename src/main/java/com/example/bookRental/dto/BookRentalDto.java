package com.example.bookRental.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class BookRentalDto  {
   private Integer id;
   private Integer bookId;
   private Integer transactionCode;
   private LocalDate fromDate;
   private LocalDate toDate;
   private String status;
   private Integer memberId;
}
