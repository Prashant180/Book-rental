package com.example.bookRental.controller;

import com.example.bookRental.CustomResponse;
import com.example.bookRental.dto.MemberDto;
import com.example.bookRental.model.Member;
import com.example.bookRental.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/member")
public class MemberController {

    @Autowired
    private MemberService service;

    @GetMapping("/all")
    public CustomResponse<List<MemberDto>> getMembers(){
        return CustomResponse.success(service.getAllMember());
    }

    @PostMapping("/add")
    public CustomResponse<MemberDto> addMember(@RequestBody MemberDto memberDto){
        return CustomResponse.success(service.addMember(memberDto));
    }

    @GetMapping("/get-by-id/{id}")
    public CustomResponse<MemberDto> getMember(@PathVariable Integer id){
        return CustomResponse.success(service.getMemberById(id));
    }

    @DeleteMapping("/delete/{id}")
    public CustomResponse<MemberDto> deleteMember(@PathVariable Integer id){
        service.deleteMember(id);
        return CustomResponse.success("Member deleted with Id "+id);
    }
}
