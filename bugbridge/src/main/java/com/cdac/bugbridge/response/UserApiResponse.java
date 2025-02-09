package com.cdac.bugbridge.response;

import com.cdac.bugbridge.dto.UserResponse;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import java.time.LocalDateTime;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({ "timestamp", "status", "message", "path", "data", "userList" })
public class UserApiResponse {

  @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
  private LocalDateTime timestamp;
  private int status;
  private String message;
  private String path;
  private UserResponse userResponse;
  private List<UserResponse> userList;

  // Single constructor for all cases
  public UserApiResponse(int status, String message, String path, UserResponse userResponse,
      List<UserResponse> userList) {
    this.timestamp = LocalDateTime.now();
    this.status = status;
    this.message = message;
    this.path = path;
    this.userResponse = userResponse;
    this.userList = userList;
  }

  // ctor for authentication
  public UserApiResponse(int status, String message, String path) {
    this.timestamp = LocalDateTime.now();
    this.status = status;
    this.message = message;
    this.path = path;
  }

  public UserApiResponse(int status, String message, String path, UserResponse userResponse) {
    this.status = status;
    this.message = message;
    this.path = path;
    this.userResponse = userResponse;

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
    return userResponse;
  }

  public void setData(UserResponse userResponse) {
    this.userResponse = userResponse;
  }

  public List<UserResponse> getUserList() {
    return userList;
  }

  public void setUserList(List<UserResponse> userList) {
    this.userList = userList;
  }

}
