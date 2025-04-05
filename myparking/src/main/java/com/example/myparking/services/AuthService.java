package com.example.myparking.services;

import com.example.myparking.dto.LoginRequest;
import com.example.myparking.dto.RegisterRequest;
import com.example.myparking.models.Role;
import com.example.myparking.models.User;
import com.example.myparking.repositories.RoleRepository;
import com.example.myparking.repositories.UserRepository;
import com.example.myparking.security.CustomUserDetails;
import com.example.myparking.security.JwtUtils;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
public class AuthService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtils jwtUtils;

    public AuthService(UserRepository userRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder, JwtUtils jwtUtils) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtils = jwtUtils;
    }

    public Map<String, Object> registerUser(RegisterRequest request) {
        Map<String, Object> response = new HashMap<>();

        if (userRepository.existsByEmail(request.getEmail())) {
            response.put("success", false);
            response.put("error", "Email đã được sử dụng!");
            return response;
        }
        if (userRepository.existsByUsername(request.getUsername())) {
            response.put("success", false);
            response.put("error", "Username đã tồn tại!");
            return response;
        }

        User user = new User();
        user.setUsername(request.getUsername());
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setCreatedAt(new Date());
        user.setBalance(0.0);
        user.setIsLocked(0);
        user.setIsVerified(0);

        Role userRole = roleRepository.findByName("CUSTOMER")
                .orElseThrow(() -> new RuntimeException("Role không tồn tại"));
        user.setRoles(Collections.singleton(userRole));

        userRepository.save(user);

        response.put("success", true);
        response.put("message", "Đăng ký thành công!");
        return response;
    }


    public Map<String, Object> loginUser(LoginRequest request) {
        Map<String, Object> response = new HashMap<>();

        User user = userRepository.findByUsername(request.getUsername())
                .orElse(null);

        if (user == null || !passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            response.put("success", false);
            response.put("error", "Tên đăng nhập hoặc mật khẩu không đúng!");
            return response;
        }

        // Convert User to UserDetails
        CustomUserDetails userDetails = new CustomUserDetails(user);

        // Generate JWT token
        String token = jwtUtils.generateToken(userDetails);

        response.put("success", true);
        response.put("message", "Đăng nhập thành công!");
        response.put("token", token);
        return response;
    }
}
