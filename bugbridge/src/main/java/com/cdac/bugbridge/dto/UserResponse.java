package com.cdac.bugbridge.dto;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserResponse {

  private Long id;
  private String name;
  private String email;
  private String role;
  private Boolean isPresent;

  public UserResponse() {

  }

  // Constructor
  public UserResponse(Long id, String role, Boolean isPresent) {
    this.id = id;
    this.role = role;
    this.isPresent = isPresent;
  }

  // Constructor
  public UserResponse(String name, String email, String role) {
    this.name = name;
    this.email = email;
    this.role = role;
  }

  // Constructor
  public UserResponse(Long id, String name, String email, String role) {
    this.id = id;
    this.name = name;
    this.email = email;
    this.role = role;
  }

  // Getters and Setters
  public Long getId() {
    return id;
  }

  public void setId(Long id) {
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

  public String getRole() {
    return role;
  }

  public void setRole(String role) {
    this.role = role;
  }

  public Boolean getIsPresent() {
    return isPresent;
  }

  public void setIsPresent(Boolean isPresent) {
    this.isPresent = isPresent;
  }

  @Override
  public String toString() {
    return "User{" +
        "id=" + id +
        ", name='" + name + '\'' +
        ", email='" + email + '\'' +
        ", role=" + role +
        '}';
  }

}
