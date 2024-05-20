package com.example.bookRental.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name="author")
public class Author {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String authorName;
    private String email;
    private String mobileNumber;
    private Boolean active=true;

    public Author(String authorName, String email, String mobileNumber) {
        this.authorName=authorName;
        this.mobileNumber=mobileNumber;
        this.email=email;
    }

    public Author(Integer id, String authorName, String email, String mobileNumber) {
        this.id=id;
        this.authorName=authorName;
        this.email=email;
        this.mobileNumber=mobileNumber;
    }
}