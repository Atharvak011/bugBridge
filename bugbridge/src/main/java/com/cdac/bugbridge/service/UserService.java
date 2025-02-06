package com.cdac.bugbridge.service;

import com.cdac.bugbridge.dto.UserDTO;
import com.cdac.bugbridge.dto.UserResponse;
import com.cdac.bugbridge.exception.UserException;
import com.cdac.bugbridge.models.User;
import com.cdac.bugbridge.response.UserApiResponse;

import jakarta.validation.Valid;

public interface UserService {

  // Add new user (registration) based on developer and tester roles, cannot add
  // admin
  void addUser(UserDTO userDTO) throws UserException.UserAlreadyExistsException;

  // finding a user by userId -- DONE
  UserApiResponse findUserById(Long userId);

  // for login authentication
  // Only Developer and Tester roles allowed
  UserResponse validUserByEmail(UserDTO userDTO);

  // finding a user by userId -- DONE
  boolean findUserById(UserDTO userDTO);

  // Delete user, only admin can delete
  void deleteUserByEmail(String emailId); // Only admin can delete

  // Delete user, only admin can delete
  UserApiResponse deleteUserById(Long userId); // Only admin can delete

  // List of all testers
  UserApiResponse listByRole(String role); // Return a list of testers

  // List of all users, only admin privilege -- DONE
  UserApiResponse listAllUsers();

  // Changing details of user including password, email, role -- DONE
  UserApiResponse updateUser(Long userId, UserDTO userDTO);

  // Omkar Modifing the Code

}
