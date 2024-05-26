package com.example.bookRental.controller;

import com.example.bookRental.CustomException;
import com.example.bookRental.CustomResponse;
import com.example.bookRental.dto.UserRequest;
import com.example.bookRental.model.UserMember;
import com.example.bookRental.service.impl.JwtService;
import com.example.bookRental.service.impl.UserMemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserMemberController {

    @Autowired
    private UserMemberService userMemberService;

    @Autowired
    private JwtService jwtService;

    @Autowired
    AuthenticationManager authenticationManager;

    @PostMapping("/register")
    public CustomResponse<UserMember> register(@RequestBody UserRequest userMember){
        return CustomResponse.success(userMemberService.saveUserMember(userMember));
    }

    @PostMapping("/login")
    public CustomResponse<String> login(@RequestBody UserMember userMember){
        try {
            Authentication authentication=authenticationManager
                    .authenticate(new UsernamePasswordAuthenticationToken(userMember.getUserName(),userMember.getPassword()));

            if (authentication.isAuthenticated()){
                String token=jwtService.generateToken(userMember.getUserName());
                return CustomResponse.<String>builder().data("token = "+token).message("Login Successfull!").success(true).build();
            }
        } catch (AuthenticationException e){
            throw new CustomException(HttpStatus.UNAUTHORIZED, "Bad credentials. Login for user "+userMember.getUserName()+" failed");
        }

        return null;
    }
}
