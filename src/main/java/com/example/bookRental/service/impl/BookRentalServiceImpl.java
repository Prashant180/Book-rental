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
import com.example.bookRental.projection.BookRentalProjection;
import com.example.bookRental.service.BookRentalService;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
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
    public List<BookRentalProjection> getAllRentedBooksWithName() {
        return bookRentalRepo.findBookRentalWithName();
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
    public BookRentalProjection getRentedBookByCode(int code) {
        BookRentalProjection bookRental=bookRentalRepo.findBookRentalByCodeWithName(code);
        if (bookRental == null){
            throw new CustomException(HttpStatus.BAD_REQUEST,"Invalid Transaction Code!");
        }
        return bookRental;
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

        if (bookRented != null && !(Objects.equals(bookRented.getStatus(), "closed"))) {
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

    public void downloadRentedData(){
        List<BookRental> bookRentals = bookRentalRepo.findAll();

        XSSFWorkbook workbook=new XSSFWorkbook();

        CellStyle style=workbook.createCellStyle();
        Font font=workbook.createFont();
        font.setBold(true);
        font.setItalic(true);
        style.setFont(font);

        XSSFSheet spreadsheet1=workbook.createSheet("Book rental data");

        XSSFRow row=spreadsheet1.createRow(0);
        XSSFCell cell;

        cell=row.createCell(0);
        cell.setCellStyle(style);
        cell.setCellValue("ID");

        cell=row.createCell(1);
        cell.setCellStyle(style);
        cell.setCellValue("Book Id");

        cell=row.createCell(2);
        cell.setCellStyle(style);
        cell.setCellValue("Member ID");

        cell=row.createCell(3);
        cell.setCellStyle(style);
        cell.setCellValue("From Date");

        cell=row.createCell(4);
        cell.setCellStyle(style);
        cell.setCellValue("To Date");

        cell=row.createCell(5);
        cell.setCellStyle(style);
        cell.setCellValue("Status");

        cell=row.createCell(6);
        cell.setCellStyle(style);
        cell.setCellValue("Transaction Code");

        int i=1;

        for (BookRental bookRental: bookRentals){
            row=spreadsheet1.createRow(i);

            cell=row.createCell(0);
            cell.setCellValue(bookRental.getId());

            cell=row.createCell(1);
            cell.setCellValue(bookRental.getBook().getId());

            cell=row.createCell(2);
            cell.setCellValue(bookRental.getMember().getId());

            cell=row.createCell(3);
            cell.setCellValue(bookRental.getFromDate().toString());

            cell=row.createCell(4);
            cell.setCellValue(bookRental.getToDate().toString());

            cell=row.createCell(5);
            cell.setCellValue(bookRental.getStatus());

            cell=row.createCell(6);
            cell.setCellValue(bookRental.getTransactionCode());

            i++;
        }
        spreadsheet1.autoSizeColumn(0);
        spreadsheet1.autoSizeColumn(1);
        spreadsheet1.autoSizeColumn(2);
        spreadsheet1.autoSizeColumn(3);
        spreadsheet1.autoSizeColumn(4);
        spreadsheet1.autoSizeColumn(5);
        spreadsheet1.autoSizeColumn(6);

        try (FileOutputStream output= new FileOutputStream(new File("d:/rentedData.xlsx"))){
            workbook.write(output);
        } catch (IOException e){
            System.out.println(e);
        }
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
