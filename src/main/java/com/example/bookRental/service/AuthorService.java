package com.example.bookRental.service;

import com.example.bookRental.dto.AuthorDto;
import com.example.bookRental.model.Author;

import java.util.List;

public interface AuthorService {

    AuthorDto addAuthor(AuthorDto authorDto);

    List<AuthorDto> getAllAuthors();

    AuthorDto getAuthorById(int id);

    void deleteAuthor(int id);
}
