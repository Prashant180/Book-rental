package com.example.bookRental.mapper;

import com.example.bookRental.dto.MemberDto;
import com.example.bookRental.model.Member;

import java.util.Optional;

public class MemberMapper {
    public static MemberDto mapToMemberDto(Member member){
        MemberDto memberDto=new MemberDto(
                member.getId(),
                member.getEmail(),
                member.getMemberName(),
                member.getMobileNumber(),
                member.getAddress()
                );
        return memberDto;
    }

    public static Member mapToMember(MemberDto memberDto){
        Member member=new Member(
                memberDto.getEmail(),
                memberDto.getMemberName(),
                memberDto.getMobileNumber(),
                memberDto.getAddress()
        );
        return member;
    }
}
