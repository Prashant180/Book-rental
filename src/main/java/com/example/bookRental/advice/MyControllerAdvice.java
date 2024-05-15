package com.example.bookRental.advice;

import com.example.bookRental.CustomException;
import com.example.bookRental.CustomResponse;
import com.example.bookRental.ErrorResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;

@RestControllerAdvice
public class MyControllerAdvice extends ResponseEntityExceptionHandler {
    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        return super.handleMethodArgumentNotValid(ex, headers, status, request);
    }

    @ExceptionHandler(SQLIntegrityConstraintViolationException.class)
    public ResponseEntity<ErrorResponse> handleSql(SQLIntegrityConstraintViolationException sqlIntegrityConstraintViolationException){
        ErrorResponse errorResponse= new ErrorResponse(sqlIntegrityConstraintViolationException.getMessage());
        return ResponseEntity.status(sqlIntegrityConstraintViolationException.getErrorCode()).body(errorResponse);
    }

    @ExceptionHandler(CustomException.class)
    public ResponseEntity<ErrorResponse> handleCustom(CustomException customException) {
        ErrorResponse errorResponse = new ErrorResponse(customException.getMessage());
        return ResponseEntity.status(customException.getStatusCode()).body(errorResponse);
    }

}

