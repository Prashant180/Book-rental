package com.example.bookRental.dao;

import com.example.bookRental.model.UserMember;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserMemberRepo extends JpaRepository<UserMember, Integer> {
    UserMember findByUserName(String userName);
}
