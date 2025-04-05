package com.example.myparking.dto;

import java.util.Set;

public class RoleDTO {
    private Set<String> roles;

    public RoleDTO(Set<String> roles) {
        this.roles = roles;
    }
    public Set<String> getRoles() {
        return roles;
    }
}
