package com.example.bookRental;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CustomException extends RuntimeException{

    private Boolean success=false;
    private String message;
    private String  code;
    private HttpStatus statusCode;

    public CustomException(String code) {
        this.code = code;
    }
    public CustomException(HttpStatus statusCode, String message) {
        this.statusCode = statusCode;
        this.message = message;
    }
    public CustomException(HttpStatus statusCode, String code, String message) {
        this.statusCode = statusCode;
        this.message = message;
        this.code = code;
    }

}
