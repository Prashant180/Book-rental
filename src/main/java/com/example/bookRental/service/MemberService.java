package com.example.bookRental.service;

import com.example.bookRental.dto.MemberDto;
import com.example.bookRental.model.Member;

import java.util.List;

public interface MemberService {

    List<MemberDto> getAllMember();

    MemberDto getMemberById(int id);

    MemberDto addMember(MemberDto member);

    void deleteMember(int id);
}
