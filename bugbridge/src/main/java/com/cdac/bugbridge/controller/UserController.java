package com.cdac.bugbridge.controller;

import com.cdac.bugbridge.exception.UserException;
import com.cdac.bugbridge.service.UserService;
import com.cdac.bugbridge.util.UserRole;
import org.springframework.web.bind.annotation.*;

import com.cdac.bugbridge.dto.UserDTO;
import com.cdac.bugbridge.dto.UserResponse;
import com.cdac.bugbridge.response.UserApiResponse;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;

@RestController
@RequestMapping("/api")
public class UserController {

  private final UserService userService;

  @Autowired
  public UserController(UserService userService) {
    this.userService = userService;
  }

  // want only admin role to access this api -- DONE
  @GetMapping("/admin/users")
  public ResponseEntity<UserApiResponse> getAllUsers() {
    UserApiResponse userApiResponse = userService.listAllUsers();
    return ResponseEntity.ok(userApiResponse);
  }

  // only admin can delete
  @DeleteMapping("/admin/deleteUser/{emailId}")
  public ResponseEntity<UserApiResponse> addUser(@PathVariable String emailId) {
    try {
      userService.deleteUserByEmail(emailId);
      UserResponse userResponse = new UserResponse(null, null, null, null);
      return ResponseEntity.ok(new UserApiResponse(200, "Success", "/dashboard", userResponse, null));
    } catch (Exception ex) {
      return ResponseEntity.status(400).body(new UserApiResponse(400, ex.getMessage(), "/api/users", null, null));
    }
  }

  // Registering a new User
  @PostMapping("/register")
  public ResponseEntity<UserApiResponse> addUser(@RequestBody UserDTO userDTO) {
    try {
      userService.addUser(userDTO);
      UserResponse userResponse = new UserResponse(userDTO.getName(), userDTO.getEmail(),
          userDTO.getRole());
      return ResponseEntity.ok(new UserApiResponse(200, "Success", "/api/login", userResponse, null));
    } catch (UserException.UserAlreadyExistsException ex) {
      return ResponseEntity.status(400).body(new UserApiResponse(400, ex.getMessage(), "/api/register", null, null));
    }
  }

  // login Validation
  @PostMapping("/authenticate")
  public ResponseEntity<UserApiResponse> authenticateUser(@RequestBody UserDTO userDTO) {
    boolean val = userService.findUserByEmail(userDTO);
    if (val) {
      return ResponseEntity.ok(new UserApiResponse(200, "Authentication Success", "/api/dashboard"));
    }
    return ResponseEntity
        .ok(new UserApiResponse(200, "Login credentials Incorrect", "/api/login"));
  }

  @PatchMapping("/authorized/profileInfo/updateUserDetails")
  public ResponseEntity<UserApiResponse> updateUserDetails(@RequestBody UserDTO userDTO) {
    return ResponseEntity.ok(userService.updateUser(userDTO.getId(), userDTO));

  }

}
