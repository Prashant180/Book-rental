package com.example.bookRental.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name="member")
public class Member {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String email;
    private String memberName;
    private String mobileNumber;
    private String address;
    private Boolean active=true;

    public Member(String email, String memberName, String mobileNumber, String address) {
        this.email=email;
        this.memberName=memberName;
        this.mobileNumber=mobileNumber;
        this.address=address;
    }
}
