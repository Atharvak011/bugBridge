package com.cdac.bugbridge.controller;

import com.cdac.bugbridge.service.UserService;

import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.*;

import com.cdac.bugbridge.dto.UserDTO;
import com.cdac.bugbridge.dto.UserResponse;
import com.cdac.bugbridge.response.UserApiResponse;

import org.springframework.http.ResponseEntity;

@RestController
@RequestMapping("/api/users")
public class UserController {

  private final UserService userService;

  public UserController(UserService userService) {
    this.userService = userService;
  }

  @PostMapping("/register")
  public ResponseEntity<UserApiResponse> addUser(@Valid @RequestBody UserDTO userDTO) {
    userService.addUser(userDTO);
    UserResponse userResponse = new UserResponse(userDTO.getName(), userDTO.getEmail(), userDTO.getRole());
    return ResponseEntity.ok(new UserApiResponse(200, "Success", "/api/users/login", userResponse, null));
  }

  @PostMapping("/authenticate")
  public ResponseEntity<UserApiResponse> authenticateUser(@Valid @RequestBody UserDTO userDTO) {
    UserResponse response = userService.validUserByEmail(userDTO);
    if (response.getIsPresent() != false) {
      return ResponseEntity.ok(new UserApiResponse(200, "Authentication Success", "/api/dashboard", response));
    }
    return ResponseEntity
        .ok(new UserApiResponse(200, "Login credentials Incorrect", "/api/login", response));
  }

  @PatchMapping("/updateUserDetails")
  public ResponseEntity<UserApiResponse> updateUserDetails(@Valid @RequestBody UserDTO userDTO) {
    return ResponseEntity.ok(userService.updateUser(userDTO.getId(), userDTO));
  }

  @DeleteMapping("/admin/deleteUser/{userId}")
  public ResponseEntity<UserApiResponse> deleteUser(@PathVariable("userId") Long userId) {
    try {
      UserApiResponse response = userService.deleteUserById(userId);
      return ResponseEntity.ok(response);
    } catch (Exception ex) {
      return ResponseEntity.status(400).body(new UserApiResponse(400, ex.getMessage(), "/api/users", null, null));
    }
  }

  @GetMapping("/singleUserById/{userId}")
  public ResponseEntity<UserApiResponse> getOneUser(@PathVariable Long userId) {
    UserApiResponse userApiResponse = userService.findUserById(userId);
    return ResponseEntity.ok(userApiResponse);
  }

  @GetMapping("/admin/allUsers")
  public ResponseEntity<UserApiResponse> getAllUsers() {
    UserApiResponse userApiResponse = userService.listAllUsers();
    return ResponseEntity.ok(userApiResponse);
  }

  @GetMapping("/{role}")
  public ResponseEntity<UserApiResponse> getUsersByRole(@PathVariable("role") String role) {
    return ResponseEntity.ok(userService.listByRole(role));
  }
}
