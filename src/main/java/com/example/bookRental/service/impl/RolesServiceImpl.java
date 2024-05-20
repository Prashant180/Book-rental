package com.example.bookRental.service.impl;

import com.example.bookRental.dao.RolesRepo;
import com.example.bookRental.model.Roles;
import com.example.bookRental.service.RolesService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class RolesServiceImpl implements RolesService {

    @Autowired
    private RolesRepo rolesRepo;

    @Override
    public List<Roles> getAllRoles() {
        return rolesRepo.findAll();
    }

    @Override
    public Roles getRoleById(Integer id) {
        return rolesRepo.findById(id).get();
    }

    @Override
    public Void addRole(Roles role) {
        rolesRepo.save(role);
        return null;
    }
}
