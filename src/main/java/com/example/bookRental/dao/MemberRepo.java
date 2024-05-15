package com.example.bookRental.dao;

import com.example.bookRental.model.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MemberRepo extends JpaRepository<Member, Integer> {
    Member findByEmail(String email);
    Member findByMobileNumber(String mobileNumber);
    List<Member> findByActiveTrue();
    Member findByIdAndActiveTrue(int id);
}
