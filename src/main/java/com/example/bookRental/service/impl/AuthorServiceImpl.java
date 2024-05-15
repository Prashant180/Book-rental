package com.example.bookRental.service.impl;

import com.example.bookRental.CustomException;
import com.example.bookRental.dao.AuthorRepo;
import com.example.bookRental.dto.AuthorDto;
import com.example.bookRental.mapper.AuthorMapper;
import com.example.bookRental.model.Author;
import com.example.bookRental.service.AuthorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AuthorServiceImpl implements AuthorService {

    @Autowired
    private AuthorRepo authorRepo;

    @Override
    public AuthorDto addAuthor(AuthorDto authorDto) {
        if (authorRepo.findByEmail(authorDto.getEmail()) != null) {
            throw new CustomException(HttpStatus.BAD_REQUEST, "User with this email already exist!");
        }
        if (authorRepo.findByMobileNumber(authorDto.getMobileNumber()) != null) {
            throw new CustomException(HttpStatus.BAD_REQUEST, "User with this number already exist!");
        }
        if (!authorDto.getAuthorName().matches("^[a-zA-Z\s]+$")) {
            throw new CustomException(HttpStatus.NOT_ACCEPTABLE, "Invalid name format!");
        }
        if (!authorDto.getEmail().matches("^[a-zA-Z0-9_]+@[a-z]+\\.[a-z]{2,6}$")) {
            throw new CustomException(HttpStatus.NOT_ACCEPTABLE, "Invalid email format!");
        }
        if (!authorDto.getMobileNumber().matches("^[0-9]{10}$")) {
            throw new CustomException(HttpStatus.NOT_ACCEPTABLE, "Invalid phone number!");
        }
        Author author = AuthorMapper.mapToAuthor(authorDto);
        Author savedAuthor = authorRepo.save(author);
        return AuthorMapper.mapToAuthorDto(savedAuthor);
    }

    @Override
    public List<AuthorDto> getAllAuthors() {
        List<Author> authors = authorRepo.findByActiveTrue();
        return authors.stream().map(AuthorMapper::mapToAuthorDto).collect(Collectors.toList());
    }

    @Override
    public AuthorDto getAuthorById(int id) {
        Author author = authorRepo.findByIdAndActiveTrue(id);
        if (author == null) {
            throw new CustomException(HttpStatus.NOT_FOUND, "Invalid Author Id!");
        }
        return AuthorMapper.mapToAuthorDto(author);
    }

    @Override
    public void deleteAuthor(int id) {
        Author author = authorRepo.findById(id).get();
        author.setActive(false);
        authorRepo.save(author);
    }
}
