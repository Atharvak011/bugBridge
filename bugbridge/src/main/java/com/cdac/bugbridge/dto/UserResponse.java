package com.cdac.bugbridge.dto;

import org.springframework.stereotype.Service;

import com.cdac.bugbridge.util.UserRole;

//@Service
public class UserResponse {


  private Integer id;
  private String name;
  private String email;
  private UserRole role;

  // Constructor
  public UserResponse( String name, String email, UserRole role) {
    this.name = name;
    this.email = email;
    this.role = role;
  }
  // Constructor
  public UserResponse(Integer id, String name, String email, UserRole role) {
    this.id = id;
    this.name = name;
    this.email = email;
    this.role = role;
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

}
