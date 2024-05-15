package com.example.bookRental.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;
import java.time.LocalDate;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@Builder
@Table(name="book")
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String bookName;
    private Integer noOfPages;
    private Integer isbn;
    private Integer rating;
    private Integer stock;
    private String photo;
    private LocalDate publishedDate;
    private Boolean active=true;


    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;


    @ManyToMany
    private List<Author> authors;

    public Book(Integer id, String bookName, Integer noOfPages, Integer isbn, Integer rating, Integer stock, String photo, LocalDate publishedDate) {
    }

}
