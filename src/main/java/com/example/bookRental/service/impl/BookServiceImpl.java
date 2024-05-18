package com.example.bookRental.service.impl;

import com.example.bookRental.CustomException;
import com.example.bookRental.dao.AuthorRepo;
import com.example.bookRental.dao.BookRepo;
import com.example.bookRental.dao.CategoryRepo;
import com.example.bookRental.dto.BookDto;
import com.example.bookRental.dto.BookRequest;
import com.example.bookRental.mapper.BookMapper;
import com.example.bookRental.model.Author;
import com.example.bookRental.model.Book;
import com.example.bookRental.model.Category;
import com.example.bookRental.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class BookServiceImpl implements BookService {

    @Autowired
    private BookRepo bookRepo;

    @Autowired
    private AuthorRepo authorRepo;

    @Autowired
    private CategoryRepo categoryRepo;

    @Override
    public List<BookDto> getAllBooks() {
        List<Book> books = bookRepo.findByActiveTrue();
        return books.stream().map(BookMapper::mapToBookDto).collect(Collectors.toList());
    }

    @Override
    public BookDto getBookById(Integer id) {
        Book book = bookRepo.findByIdAndActiveTrue(id);
        if (book == null) {
            throw new CustomException(HttpStatus.NOT_FOUND, "Invalid Book Id: " + id);
        }
//                .orElseThrow(()-> new CustomException(HttpStatus.NOT_FOUND,"Invalid Book Id: "+id));
        return BookMapper.mapToBookDto(book);
    }

    @Override
    public BookDto addBook(BookRequest bookRequest) {
        LocalDate currentDate = LocalDate.now();

        Book book = bookRepo.findByBookName(bookRequest.getBookName().trim());
        if (book != null ){
            throw new CustomException(HttpStatus.BAD_REQUEST,"Book name already exists!");
        }

        Book book1 = new Book();
        List<Author> authors = new ArrayList<>();

        if (bookRequest.getCategoryId() != null) {
            Optional<Category> category = categoryRepo.findById(bookRequest.getCategoryId());
            if (category.isEmpty()) {
                throw new CustomException(HttpStatus.BAD_REQUEST, "Invalid category Id!");
            } else {
                book1.setCategory(category.get());
            }
        } else {
            throw new CustomException(HttpStatus.BAD_REQUEST, "Category cannot be empty!");
        }

        if (bookRequest.getAuthorsId().isEmpty()){
            throw new CustomException(HttpStatus.BAD_REQUEST,"Author Id Cannot be empty!");
        }

        if (!bookRequest.getBookName().trim().matches("^[a-zA-Z\s]+$")) {
            throw new CustomException(HttpStatus.NOT_ACCEPTABLE, "Invalid name format!");
        }
        if (bookRequest.getStock() < 1 ) {
            throw new CustomException(HttpStatus.BAD_REQUEST, "Stock cannot be less than 1!");
        }
        if (bookRequest.getNoOfPages() < 1) {
            throw new CustomException(HttpStatus.BAD_REQUEST, "Book must have pages!");
        }
        if (bookRequest.getRating() < 0 || bookRequest.getRating() > 10) {
            throw new CustomException(HttpStatus.BAD_REQUEST, "Rating must be between 0-10!");
        }

        book1.setBookName(bookRequest.getBookName());
        book1.setIsbn(bookRequest.getIsbn());
        book1.setStock(bookRequest.getStock());
        book1.setNoOfPages(bookRequest.getNoOfPages());
        book1.setRating(bookRequest.getRating());

        if (bookRequest.getPublishedDate().isBefore(currentDate)) {
            book1.setPublishedDate(bookRequest.getPublishedDate());
        } else {
            throw new CustomException(HttpStatus.BAD_REQUEST, "Published date cannot be in future!");
        }
        book1.setPhoto(bookRequest.getPhoto());
        for (int authorId : bookRequest.getAuthorsId()) {
            Optional<Author> author = authorRepo.findById(authorId);
            if (author.isEmpty()){
                throw new CustomException(HttpStatus.BAD_REQUEST,"Invalid author id!");
            }
            authors.add(author.get());
        }
        book1.setAuthors(authors);
        Book savedBook = bookRepo.save(book1);
        return BookMapper.mapToBookDto(savedBook);
    }

    public BookDto updateBook(BookDto bookDto){
        Optional<Book> book=bookRepo.findById(bookDto.getId());
        if(book.isEmpty()){
            throw new CustomException(HttpStatus.BAD_REQUEST,"Book not found to update!");
        }
        if (bookDto.getStock() < 1) {
            throw new CustomException(HttpStatus.BAD_REQUEST, "Stock cannot be less than 1!");
        }
        if (bookDto.getRating() < 0 || bookDto.getRating() > 10) {
            throw new CustomException(HttpStatus.BAD_REQUEST, "Rating must be between 0-10!");
        }
        book.get().setStock(bookDto.getStock());
        book.get().setRating(bookDto.getRating());
        Book savedBook=bookRepo.save(book.get());
        return BookMapper.mapToBookDto(savedBook);
    }

    @Override
    public void deleteBook(Integer id) {
        Optional<Book> book = bookRepo.findById(id);
        if (book.isEmpty()) {
            throw new CustomException(HttpStatus.BAD_REQUEST, "Invalid Book Id");
        }
        if (book.get().getActive()) {
            book.get().setActive(false);
        } else {
            throw new CustomException(HttpStatus.BAD_REQUEST, "Book already deleted with id " + id);
        }
        bookRepo.save(book.get());
    }
}
