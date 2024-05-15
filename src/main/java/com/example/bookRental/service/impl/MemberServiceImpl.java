package com.example.bookRental.service.impl;

import com.example.bookRental.CustomException;
import com.example.bookRental.dao.MemberRepo;
import com.example.bookRental.dto.MemberDto;
import com.example.bookRental.mapper.MemberMapper;
import com.example.bookRental.model.Member;
import com.example.bookRental.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class MemberServiceImpl implements MemberService {

    @Autowired
    private MemberRepo memberRepo;

    @Override
    public List<MemberDto> getAllMember() {
        List<Member> members = memberRepo.findByActiveTrue();
        return members.stream().map(MemberMapper::mapToMemberDto).collect(Collectors.toList());
    }

    @Override
    public MemberDto getMemberById(int id) {
        Member member = memberRepo.findByIdAndActiveTrue(id);
        if (member == null) {
            throw new CustomException(HttpStatus.NOT_FOUND, "Invalid Member Id!");
        }
        return MemberMapper.mapToMemberDto(member);
    }

    @Override
    public MemberDto addMember(MemberDto memberDto) {
        if (memberRepo.findByEmail(memberDto.getEmail()) != null) {
            throw new CustomException(HttpStatus.BAD_REQUEST, "User with this email already exist!");
        }
        if (memberRepo.findByMobileNumber(memberDto.getMobileNumber()) != null) {
            throw new CustomException(HttpStatus.BAD_REQUEST, "User with this number already exist!");
        }

        if (!memberDto.getMemberName().matches("^[a-zA-Z\s]+$")) {
            throw new CustomException(HttpStatus.NOT_ACCEPTABLE, "Invalid name format!");
        }
        if (!memberDto.getEmail().matches("^[a-zA-Z0-9_]+@[a-z]+\\.[a-z]{2,6}$")) {
            throw new CustomException(HttpStatus.NOT_ACCEPTABLE, "Invalid email format!");
        }
        if (!memberDto.getMobileNumber().matches("^[0-9]{10}$")) {
            throw new CustomException(HttpStatus.NOT_ACCEPTABLE, "Invalid phone number!");
        }

        Member member = MemberMapper.mapToMember(memberDto);
        Member savedMember = memberRepo.save(member);
        return MemberMapper.mapToMemberDto(savedMember);
    }

    @Override
    public void deleteMember(int id) {
        Optional<Member> member = memberRepo.findById(id);
        if (member.isEmpty()) {
            throw new CustomException(HttpStatus.BAD_REQUEST, "Invalid Member Id");
        }
        if (member.get().getActive()) {
            member.get().setActive(false);
        } else {
            throw new CustomException(HttpStatus.BAD_REQUEST, "Member already deleted with id " + id);
        }
        memberRepo.save(member.get());
    }
}
