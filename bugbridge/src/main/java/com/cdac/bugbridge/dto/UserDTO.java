package com.cdac.bugbridge.dto;

import jakarta.validation.constraints.Pattern;
import org.springframework.stereotype.Service;

import com.cdac.bugbridge.util.UserRole;

@Service
public class UserDTO {

    private Integer id;
    private String name;
    private String email;
    private String role;

    @Pattern(regexp = "^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d)(?=.*[@$!%*?&]).{8,20}$", message = "Password must be 8-20 characters long, contain at least one uppercase letter,"
            +
            " one lowercase letter, one digit, and one special character.")
    private String password;

    // Default constructor
    public UserDTO() {
    }

    // constructor for Login details
    public UserDTO(String email, String password) {
        this.email = email;
        this.password = password;
    }

    // Constructor with fields
    public UserDTO(Integer id, String name, String email, String role, String password) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.role = role;
        this.password = password;
    }

    // Getters and Setters
    public Integer getId() {
        return id;
    }

    // public void setId(Integer id) {
    // this.id = id;
    // }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "UserDTO{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", role=" + role +
                ", password='" + password + '\'' +
                '}';
    }
}
