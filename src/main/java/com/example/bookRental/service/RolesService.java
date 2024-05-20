package com.example.bookRental.service;

import com.example.bookRental.model.Roles;

import java.util.List;

public interface RolesService {
    List<Roles> getAllRoles();
    Roles getRoleById(Integer id);
    Void addRole(Roles role);
}
