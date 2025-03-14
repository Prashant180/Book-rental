package com.example.bookRental.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MemberDto {
    private Integer id;
    private String email;
    private String memberName;
    private String mobileNumber;
    private String address;
}
