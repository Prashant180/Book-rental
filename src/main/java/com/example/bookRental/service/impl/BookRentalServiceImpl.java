package com.example.bookRental.service.impl;

import com.example.bookRental.CustomException;
import com.example.bookRental.dao.BookRentalRepo;
import com.example.bookRental.dao.BookRepo;
import com.example.bookRental.dao.MemberRepo;
import com.example.bookRental.dto.BookRentRequest;
import com.example.bookRental.dto.BookRentalDto;
import com.example.bookRental.mapper.BookRentalMapper;
import com.example.bookRental.model.Book;
import com.example.bookRental.model.BookRental;
import com.example.bookRental.model.Member;
import com.example.bookRental.service.BookRentalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Random;
import java.util.stream.Collectors;

@Service
public class BookRentalServiceImpl implements BookRentalService {

    @Autowired
    private BookRentalRepo bookRentalRepo;

    @Autowired
    private BookRepo bookRepo;

    @Autowired
    private MemberRepo memberRepo;

    @Override
    public List<BookRentalDto> getAllRentedBooks() {
        List<BookRental> bookRentals = bookRentalRepo.findAll();
        return bookRentals.stream().map(BookRentalMapper::mapToBookRentalDto).collect(Collectors.toList());
    }

    @Override
    public BookRental getRentedBookById(int id) {
        Optional<BookRental> bookRental = bookRentalRepo.findById(id);
        return bookRental.get();
    }

    @Override
    public BookRental getRentalByMemberID(int id) {
        return bookRentalRepo.findFirstByMemberIdOrderByIdDesc(id);
    }

    @Override
    public BookRentalDto getRentedBookByCode(int code) {
        return BookRentalMapper.mapToBookRentalDto(bookRentalRepo.findByTransactionCode(code));
    }

    public static int random(Integer length) {
        Random random = new Random();
        Double num = random.nextDouble();
        Integer code = 0;
        while (true) {
            code = (int) (num * (Math.pow(10, length)));
            if (code.toString().length() == length) {
                break;
            }
        }
        return code;
    }

    @Override
    public BookRentalDto rentBook(BookRentRequest bookRentRequest) {

        Member member = memberRepo.findByIdAndActiveTrue(bookRentRequest.getMemberId());
        if (member == null) {
            throw new CustomException(HttpStatus.NOT_FOUND, "Invalid Member Id!");
        }

        if (bookRentRequest.getDays() > 30 || bookRentRequest.getDays() < 1) {
            throw new CustomException(HttpStatus.BAD_REQUEST, "Book can be rented for max 30 days and at least 1 day");
        }

        BookRental bookRented = getRentalByMemberID(bookRentRequest.getMemberId());
        BookRental bookRental = new BookRental();

        if (bookRented != null && Objects.equals(bookRented.getStatus(), "closed")) {
            throw new CustomException(HttpStatus.BAD_REQUEST, "Cannot issue new book until previous book is returned");
        }
        LocalDate currentDate = LocalDate.now();
        BookRental savedRentBook;

        Optional<Book> book = Optional.ofNullable(bookRepo.findByIdAndActiveTrue(bookRentRequest.getBookId()));
        int stock;
        if (book.isPresent()) {
            stock = book.get().getStock();
            if (stock > 0) {

                book.get().setStock(stock - 1);

                bookRental.setBook(Book.builder().id(bookRentRequest.getBookId()).build());
                bookRental.setStatus("active");
                bookRental.setFromDate(currentDate);
                bookRental.setToDate(currentDate.plusDays(bookRentRequest.getDays()));
                bookRental.setTransactionCode(BookRentalServiceImpl.random(6));
                bookRental.setMember(Member.builder().id(bookRentRequest.getMemberId()).build());
                savedRentBook = bookRentalRepo.save(bookRental);
                System.out.println("Book issued");


            } else {
                throw new CustomException(HttpStatus.BAD_REQUEST, "Book out of stock!");
            }
        } else {
            throw new CustomException(HttpStatus.NOT_FOUND, "Invalid Book Id!");
        }
        return BookRentalMapper.mapToBookRentalDto(savedRentBook);
    }

    @Override
    public void returnBook(int code) {
        BookRental bookRental = bookRentalRepo.findByTransactionCode(code);
        if (bookRental == null) {
            throw new CustomException(HttpStatus.BAD_REQUEST, "Not found! Invalid code!");
        } else {
            int stock;
            Optional<Book> book = bookRepo.findById(bookRental.getBook().getId());
            stock = book.get().getStock();
            book.get().setStock(stock + 1);
            bookRental.setStatus("closed");
            bookRentalRepo.save(bookRental);
            System.out.println("Book returned");
        }
    }
}
