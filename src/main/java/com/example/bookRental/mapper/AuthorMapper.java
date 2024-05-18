package com.example.bookRental.mapper;

import com.example.bookRental.dto.AuthorDto;
import com.example.bookRental.model.Author;

public class AuthorMapper {

    public static AuthorDto mapToAuthorDto(Author author){
        AuthorDto authorDto=new AuthorDto(
                author.getId(),
                author.getAuthorName(),
                author.getEmail(),
                author.getMobileNumber()
                );
        return authorDto;
    }

    public static Author mapToAuthor(AuthorDto authorDto){
        Author author=new Author(
                authorDto.getId(),
                authorDto.getAuthorName(),
                authorDto.getEmail(),
                authorDto.getMobileNumber()
                );
        return author;
    }

}
