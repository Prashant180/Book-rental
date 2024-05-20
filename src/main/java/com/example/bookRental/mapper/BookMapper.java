package com.example.bookRental.mapper;

import com.example.bookRental.dto.BookDto;
import com.example.bookRental.model.Author;
import com.example.bookRental.model.Book;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class BookMapper {
    public static BookDto mapToBookDto(Book book){
        Set<Author> authors=new HashSet<>();
        for (Author author: book.getAuthors()){
            authors.add(author);
        }
        List<Integer> authorIds=new ArrayList<>();
        for (Author authorId: authors){
            authorIds.add(authorId.getId());
        }

        BookDto bookDto=new BookDto(
                book.getId(),
                book.getBookName(),
                book.getNoOfPages(),
                book.getIsbn(),
                book.getRating(),
                book.getStock(),
                book.getPhoto().getPhoto(),
                book.getPublishedDate(),
                book.getCategory().getId(),
                authorIds
        );
        return bookDto;
    }

    public static Book mapToBook(BookDto bookDto){
        Book book=new Book(
                bookDto.getId(),
                bookDto.getBookName(),
                bookDto.getNoOfPages(),
                bookDto.getIsbn(),
                bookDto.getRating(),
                bookDto.getStock(),
                bookDto.getPhoto(),
                bookDto.getPublishedDate()
        );
        return book;
    }
}
