package com.cdac.bugbridge.models;

import com.cdac.bugbridge.util.UserRole;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;

@Entity
@Table(name = "users")
public class User {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;

  @Column(name = "name", nullable = false)
  @Pattern(regexp = "^[a-zA-Z ]{3,50}$", message = "Name must contain only letters and spaces, with 3-50 characters.")
  private String name;

  @Column(name = "email", unique = true, nullable = false)
  @Email(message = "Email should be valid.")
  private String email;

  @Column(name = "role", nullable = false)
  @Enumerated(EnumType.STRING)
  private UserRole role;

  @Column(name = "password", nullable = false, length = 60)
  private String password;

  // Default Constructor
  public User() {
  }

  // Parameterized Constructor for Login
  public User(String email,String password) {
    this.email = email;
    this.password = password;
  }

  // Parameterized Constructor
  public User(String name, String email, UserRole role, String password) {
    this.name = name;
    this.email = email;
    this.role = role;
    this.password = password;
  }

  // Getters and Setters
  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

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

  public UserRole getRole() {
    return role;
  }
  
  public void setRole(String role) {
    this.role = UserRole.fromString(role);
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  @Override
  public String toString() {
    return "User{" +
            "id=" + id +
            ", name='" + name + '\'' +
            ", email='" + email + '\'' +
            ", role=" + role +
            ", password='" + password + '\'' +
            '}';
  }
}
