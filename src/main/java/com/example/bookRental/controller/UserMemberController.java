package com.example.bookRental.controller;

import com.example.bookRental.CustomResponse;
import com.example.bookRental.dto.UserRequest;
import com.example.bookRental.model.UserMember;
import com.example.bookRental.service.impl.UserMemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserMemberController {

    @Autowired
    private UserMemberService userMemberService;

    @PostMapping("/register")
    public CustomResponse<UserMember> register(@RequestBody UserRequest userMember){
        return CustomResponse.success(userMemberService.saveUserMember(userMember));
    }

}
