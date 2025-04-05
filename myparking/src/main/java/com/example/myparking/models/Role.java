package com.example.myparking.models;

import jakarta.persistence.*;

import java.util.Set;
import java.util.stream.Collectors;


@Entity
@Table(name = "ROLE")
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String description;

    @ManyToMany(mappedBy = "roles")
    private Set<User> users;

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getUsers() {
        return users.stream().map(User::getUsername).collect(Collectors.joining(", "));
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setUsers(Set<User> users) {
        this.users = users;
    }
}

