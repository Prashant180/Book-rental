package com.example.bookRental.service;

import com.example.bookRental.dao.AdminRepo;
import com.example.bookRental.model.Admin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public class AdminService implements UserDetailsService {

    @Autowired
    private AdminRepo adminRepo;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Admin admin=adminRepo.findByUserName(username);
        if(admin == null){
            System.out.println("Admin not found");
            throw new UsernameNotFoundException("Admin not found");
        }

        return User
                .withDefaultPasswordEncoder()
                .username(admin.getUserName())
                .password(admin.getPassword())
                .roles(getRoles(admin))
                .build();
    }

    private String[] getRoles(Admin admin){
        if (admin.getRole() == null){
            return new String[]{"USER"};
        }
        return admin.getRole().split(",");
    }
}
