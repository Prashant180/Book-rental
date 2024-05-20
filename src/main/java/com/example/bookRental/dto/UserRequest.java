package com.example.bookRental.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class UserRequest {
    private String userName;
    private String password;
    private List<Integer> rolesId;
}
