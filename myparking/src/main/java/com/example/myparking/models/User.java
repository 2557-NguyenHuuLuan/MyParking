    package com.example.myparking.models;

    import com.fasterxml.jackson.annotation.JsonFormat;
    import jakarta.persistence.*;

    import java.util.Date;
    import java.util.Set;

    @Entity
@Table(name = "\"USER\"")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String address;
    private Double balance;

    @Column(name = "CREATED_AT")
    @JsonFormat(pattern = "dd/MM/yyyy", timezone = "Asia/Ho_Chi_Minh")
    private Date createdAt;

    private String email;

    @Column(name = "IS_LOCKED")
    private Integer isLocked;

    @Column(name = "IS_VERIFIED")
    private Integer isVerified;

    private String password;
    private String phone;
    private String username;

    @ManyToMany
    @JoinTable(
            name = "USER_ROLE", // Tên bảng trung gian
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    private Set<Role> roles;

        public Long getId() {
            return id;
        }

        public String getAddress() {
            return address;
        }

        public Double getBalance() {
            return balance;
        }

        public Date getCreatedAt() {
            return createdAt;
        }

        public String getEmail() {
            return email;
        }

        public Integer getIsLocked() {
            return isLocked;
        }

        public Integer getIsVerified() {
            return isVerified;
        }

        public String getPassword() {
            return password;
        }

        public String getPhone() {
            return phone;
        }

        public String getUsername() {
            return username;
        }

//        public String getRoles() {
//            return roles.stream()
//                    .map(Role::getName) // Lấy tên của từng Role
//                    .collect(Collectors.joining(", ")); // Ghép thành chuỗi
//        }


        public Set<Role> getRoles() {
            return roles;
        }

        public void setId(Long id) {
            this.id = id;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public void setBalance(Double balance) {
            this.balance = balance;
        }

        public void setCreatedAt(Date createdAt) {
            this.createdAt = createdAt;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public void setIsLocked(Integer isLocked) {
            this.isLocked = isLocked;
        }

        public void setIsVerified(Integer isVerified) {
            this.isVerified = isVerified;
        }

        public void setPassword(String password) {
            this.password = password;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public void setRoles(Set<Role> roles) {
            this.roles = roles;
        }
    }
