package com.example.myparking.controllers;

import com.example.myparking.models.User;
import com.example.myparking.services.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.beans.factory.annotation.Autowired;

public abstract class BaseController {

    @Autowired
    private UserService userService;

    protected Long getCurrentUserId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.getPrincipal() instanceof UserDetails userDetails) {
            User user = userService.getUserByUsername(userDetails.getUsername());
            return (user != null) ? user.getId() : null;
        }
        return null;
    }
}
