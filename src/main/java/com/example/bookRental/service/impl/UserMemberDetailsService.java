package com.example.bookRental.service.impl;

import com.example.bookRental.dao.UserMemberRepo;
import com.example.bookRental.model.UserMember;
import com.example.bookRental.model.UserPrincipal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserMemberDetailsService implements UserDetailsService {

    @Autowired
    private UserMemberRepo userMemberRepo;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserMember userMember=userMemberRepo.findByUserName(username);
        if(userMember == null){
            System.out.println("Admin not found");
            throw new UsernameNotFoundException("Admin not found");
        }

        return new UserPrincipal(userMember);
//        return User
//                .withDefaultPasswordEncoder()
//                .username(userMember.getUserName())
//                .password(userMember.getPassword())
//                .roles(getRoles(userMember))
//                .build();
    }

//    private String[] getRoles(UserMember userMember){
//        if (userMember.getRole() == null){
//            return new String[]{"USER"};
//        }
//        return userMember.getRole().split(",");
//    }
}
