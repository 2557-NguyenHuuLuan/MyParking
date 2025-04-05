package com.example.myparking.services;

import com.example.myparking.models.Role;
import com.example.myparking.repositories.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoleService {
    @Autowired
    private RoleRepository roleRepository;

    public List<Role> getAllRoles() {
        List<Role> roles = roleRepository.findAll();
        return roles;
    }
}
