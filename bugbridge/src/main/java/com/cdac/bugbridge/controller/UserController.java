package com.cdac.bugbridge.controller;

import com.cdac.bugbridge.exception.UserException;
import com.cdac.bugbridge.service.UserService;
import com.cdac.bugbridge.util.UserRole;
import org.springframework.web.bind.annotation.*;

import com.cdac.bugbridge.dao.UserDAO;
import com.cdac.bugbridge.dao.UserDAOImpl;
import com.cdac.bugbridge.dto.UserDTO;
import com.cdac.bugbridge.dto.UserResponse;
import com.cdac.bugbridge.models.User;
import com.cdac.bugbridge.response.UserApiResponse;
import com.cdac.bugbridge.service.UserServiceImpl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@RestController
@RequestMapping("/api")
public class UserController {

  private final UserService userService;

  @Autowired
  public UserController(UserService userService) {
    this.userService = userService;
  }

  // want only admin role to access this api
  @GetMapping
  public ResponseEntity<List<UserApiResponse>> getUsers(@RequestParam String param) {
    List<UserApiResponse> users = new ArrayList<>();
    UserResponse userResponse = new UserResponse(null, null, null, null);
    users.add(new UserApiResponse(0, null, "/api/users", userResponse));
    return ResponseEntity.ok(users);
  }

  // Registering a new User
  @PostMapping("/register")
  public ResponseEntity<UserApiResponse> addUser(@RequestBody UserDTO userDTO) {
    try {
      userService.addUser(userDTO);
      UserResponse userResponse = new UserResponse(userDTO.getName(), userDTO.getEmail(),
          UserRole.valueOf(userDTO.getRole()));
      return ResponseEntity.ok(new UserApiResponse(200, "Success", "/api/login", userResponse));
    } catch (UserException.UserAlreadyExistsException ex) {
      return ResponseEntity.status(400).body(new UserApiResponse(400, "Error", ex.getMessage(), "/api/register"));
    }
  }

  // login Validation
  @PostMapping("/authenticate")
  public ResponseEntity<UserApiResponse> authenticateUser(@RequestBody UserDTO userDTO) {
    boolean val = userService.findUserByEmail(userDTO);
    System.out.println(val);
    if (val) {
      return ResponseEntity.ok(new UserApiResponse(200, "Authentication Success", "/api/dashboard"));
    }
    return ResponseEntity
        .ok(new UserApiResponse(200, "Login credentials Incorrect", "Authentication Failed", "/api/login"));
  }

  // only admin can delete
  @DeleteMapping("/admin/deleteUser/{emailId}")
  public ResponseEntity<UserApiResponse> addUser(@PathVariable String emailId) {
    try {
      userService.deleteUserByEmail(emailId);
      UserResponse userResponse = new UserResponse(null, null, null, null);
      return ResponseEntity.ok(new UserApiResponse(200, "Success", "/dashboard", userResponse));
    } catch (Exception ex) {
      return ResponseEntity.status(400).body(new UserApiResponse(400, "Error", ex.getMessage(), "/api/users"));
    }
  }

}
