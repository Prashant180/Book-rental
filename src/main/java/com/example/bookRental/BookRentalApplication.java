package com.example.bookRental;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class BookRentalApplication {

	public static void main(String[] args) {
		SpringApplication.run(BookRentalApplication.class, args);
	}

}
