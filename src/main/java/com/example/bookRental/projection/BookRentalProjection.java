package com.example.bookRental.projection;

import java.time.LocalDate;

public interface BookRentalProjection {
    Integer getId();
    LocalDate getFrom_Date();
    String getStatus();
    LocalDate getTo_Date();
    Integer getTransaction_Code();
    Integer getBook_Id();
    Integer getMember_Id();
    String getBook_Name();
    String getMember_Name();
}
