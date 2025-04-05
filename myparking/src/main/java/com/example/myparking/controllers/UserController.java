package com.example.myparking.controllers;


import com.example.myparking.dto.RoleDTO;
import com.example.myparking.dto.UserDTO;
import com.example.myparking.models.User;
import com.example.myparking.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping
    public List<User> getUsers() {
        return userService.getAllUsers();
    }

    @GetMapping("/me")
    public ResponseEntity<?> getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.getPrincipal() instanceof UserDetails) {
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            Optional<User> userOptional = Optional.ofNullable(userService.getUserByUsername(userDetails.getUsername()));

            if (userOptional.isPresent()) {
                User user = userOptional.get();
                UserDTO userDTO = new UserDTO(user.getId(),user.getUsername(), user.getEmail(), user.getPhone(), user.getCreatedAt(), user.getAddress(), user.getBalance());
                return ResponseEntity.ok(userDTO);
            }
        }
        return ResponseEntity.status(401).body("Không có user đăng nhập");
    }
    @GetMapping("/get_role_of_current_user")
    public ResponseEntity<?> getRoleOfCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.getPrincipal() instanceof UserDetails) {
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            Optional<User> userOptional = Optional.ofNullable(userService.getUserByUsername(userDetails.getUsername()));
            if (userOptional.isPresent()) {
                User user = userOptional.get();
                Set<String> roles = user.getRoles().stream()
                        .map(role -> role.getName())
                        .collect(Collectors.toSet());

                RoleDTO roleDTO = new RoleDTO(roles);
                return ResponseEntity.ok(roleDTO);
            }
        }
        return ResponseEntity.status(401).body("Không có user đăng nhập");
    }
}
