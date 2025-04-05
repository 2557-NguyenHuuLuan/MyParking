package com.example.myparking.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.util.Date;

public class UserDTO {
    private Long id;
    private String username;
    private String email;
    private String phone;
    @JsonFormat(pattern = "dd/MM/yyyy", timezone = "Asia/Ho_Chi_Minh")
    private Date createdAt;
    private String address;
    private Double balance;

    public UserDTO(Long id, String username, String email, String phone, Date createdAt, String address, Double balance) {
        this.id = id;
        this.email = email;
        this.phone = phone;
        this.createdAt = createdAt;
        this.address = address;
        this.balance = balance;
        this.username = username;
    }

    // Getters & Setters
    public Long getId() { return id; }
    public String getEmail() { return email; }
    public String getPhone() { return phone; }
    public Date getCreatedAt() { return createdAt; }
    public String getAddress() { return address; }
    public Double getBalance() { return balance; }

    public String getUsername() {
        return username;
    }
}
