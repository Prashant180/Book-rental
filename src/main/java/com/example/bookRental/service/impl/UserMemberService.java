package com.example.bookRental.service.impl;

import com.example.bookRental.dao.RolesRepo;
import com.example.bookRental.dao.UserMemberRepo;
import com.example.bookRental.dto.UserRequest;
import com.example.bookRental.model.Roles;
import com.example.bookRental.model.UserMember;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserMemberService {

    @Autowired
    private UserMemberRepo userMemberRepo;
    @Autowired
    private RolesRepo rolesRepo;
    private BCryptPasswordEncoder encoder=new BCryptPasswordEncoder(12);

    public UserMember saveUserMember(UserRequest userRequest){
        UserMember userMember=new UserMember();

        List<Roles> roles=new ArrayList<>();

        for (int roleId: userRequest.getRolesId()){
            Roles role=rolesRepo.findById(roleId).get();
            roles.add(role);
        }

        userMember.setUserName(userRequest.getUserName());
        userMember.setPassword(encoder.encode(userRequest.getPassword()));
        userMember.setRole(roles);
        return userMemberRepo.save(userMember);
    }
}
