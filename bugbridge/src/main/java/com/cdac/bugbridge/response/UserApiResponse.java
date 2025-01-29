package com.cdac.bugbridge.response;

import com.cdac.bugbridge.dto.UserResponse;

import java.time.LocalDateTime;

import org.springframework.stereotype.Service;

//@Service
public class UserApiResponse {

  private LocalDateTime timestamp;
  private int status;
  private String error;
  private String message;
  private String path;
  private UserResponse userData;

  // constructor for normal response
  public UserApiResponse(int status, String message, String path) {
    this.timestamp = LocalDateTime.now();
    this.status = status;
    this.error = "OK";
    this.message = message;
    this.path = path;
  }
  // Constructor for successful response
  public UserApiResponse(int status, String message, String path, UserResponse userData) {
    this.timestamp = LocalDateTime.now();
    this.status = status;
    this.error = "OK";
    this.message = message;
    this.path = path;
    this.userData = userData;
  }

  // Constructor for error response
  public UserApiResponse(int status, String error, String message, String path) {
    this.timestamp = LocalDateTime.now();
    this.status = status;
    this.error = error;
    this.message = message;
    this.path = path;
    this.userData = null; // No data in error response
  }

  // Getters and setters
  public LocalDateTime getTimestamp() {
    return timestamp;
  }

  public void setTimestamp(LocalDateTime timestamp) {
    this.timestamp = timestamp;
  }

  public int getStatus() {
    return status;
  }

  public void setStatus(int status) {
    this.status = status;
  }

  public String getError() {
    return error;
  }

  public void setError(String error) {
    this.error = error;
  }

  public String getMessage() {
    return message;
  }

  public void setMessage(String message) {
    this.message = message;
  }

  public String getPath() {
    return path;
  }

  public void setPath(String path) {
    this.path = path;
  }

  public UserResponse getData() {
    return userData;
  }

  public void setData(UserResponse userData) {
    this.userData = userData;
  }
}
