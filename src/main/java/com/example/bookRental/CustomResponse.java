package com.example.bookRental;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class CustomResponse<T> {
    private boolean success;
    private String message;
    private T data;

    public static <T> CustomResponse<T> success(){
        return  CustomResponse.<T>builder()
                .message("Success!")
                .success(true)
                .build();
    }
    public static <T> CustomResponse<T> success(String message){
        return  CustomResponse.<T>builder()
                .message(message)
                .success(true)
                .build();
    }

    public static <T> CustomResponse<T> success(T data){
        return CustomResponse.<T>builder()
                .message("Success!")
                .data(data)
                .success(true)
                .build();
    }

    public static <T> CustomResponse<T> error(T data,String message){
        return CustomResponse.<T>builder()
                .message(message)
                .data(data)
                .success(false)
                .build();
    }
}
