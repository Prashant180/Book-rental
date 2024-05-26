package com.example.bookRental.dao;

import com.example.bookRental.model.BookRental;
import com.example.bookRental.projection.BookRentalProjection;
import com.example.bookRental.projection.BookRentalProjectionWithEmail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Repository
public interface BookRentalRepo extends JpaRepository<BookRental, Integer> {
    BookRental findFirstByMemberIdOrderByIdDesc(Integer id);
    BookRental findByTransactionCode(Integer code);

    @Query(value="SELECT br.id, br.from_date, br.status, br.to_date, br.transaction_code, br.book_id, br.member_id, b.book_name, m.member_name, m.email" +
            " FROM book_rental br" +
            " INNER JOIN book b" +
            " on br.book_id=b.id" +
            " INNER JOIN member m" +
            " on br.member_id=m.id" +
            " WHERE br.status='active'" +
            " AND br.to_date < CURDATE()"
            , nativeQuery = true)
    List<BookRentalProjectionWithEmail> findBookRentalDueDate();


    @Query(value="SELECT br.id, br.from_date, br.status, br.to_date, br.transaction_code, br.book_id, br.member_id, b.book_name, m.member_name" +
            " FROM book_rental br" +
            " INNER JOIN book b" +
            " on br.book_id=b.id" +
            " INNER JOIN member m" +
            " on br.member_id=m.id"
    , nativeQuery = true)
    List<BookRentalProjection> findBookRentalWithName();

    @Query(value="SELECT br.id, br.from_date, br.status, br.to_date, br.transaction_code, br.book_id, br.member_id, b.book_name, m.member_name" +
            " FROM book_rental br" +
            " INNER JOIN book b" +
            " on br.book_id=b.id" +
            " INNER JOIN member m" +
            " on br.member_id=m.id" +
            " WHERE br.from_date BETWEEN :from AND :to"
    , nativeQuery = true)
    List<BookRentalProjection> findBookRentalBetweenDate(LocalDate from, LocalDate to);

    @Query(value ="SELECT br.id, br.from_date, br.status, br.to_date, br.transaction_code, br.book_id, br.member_id, b.book_name, m.member_name" +
            " FROM book_rental br" +
            " INNER JOIN book b" +
            " on br.book_id=b.id" +
            " INNER JOIN member m" +
            " on br.member_id=m.id" +
            " WHERE br.transaction_code=?"
    , nativeQuery = true)
    BookRentalProjection findBookRentalByCodeWithName(Integer code);
}
