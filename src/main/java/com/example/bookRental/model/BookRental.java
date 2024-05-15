package com.example.bookRental.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class BookRental {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "book_id")
    private Book book;

    private Integer transactionCode;
    private LocalDate fromDate;
    private LocalDate toDate;
    private String status;

    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;
}
