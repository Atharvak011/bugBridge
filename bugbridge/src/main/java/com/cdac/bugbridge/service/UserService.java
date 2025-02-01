package com.cdac.bugbridge.service;

import com.cdac.bugbridge.dto.UserDTO;
import com.cdac.bugbridge.dto.UserResponse;
import com.cdac.bugbridge.exception.UserException;
import com.cdac.bugbridge.models.User;
import com.cdac.bugbridge.response.UserApiResponse;

import java.util.List;
import java.util.Optional;

public interface UserService {

  // Add new user (registration) based on developer and tester roles, cannot add
  // admin
  void addUser(UserDTO userDTO) throws UserException.UserAlreadyExistsException;

  // Only Developer and Tester roles allowed
  boolean findUserByEmail(UserDTO userDTO);

  // finding a user by userId
  boolean findUserById(UserDTO userDTO);

  // Delete user, only admin can delete
  void deleteUserByEmail(String emailId); // Only admin can delete

  // List of all testers
  List<UserDTO> listTesters(); // Return a list of testers

  // List of all developers
  List<UserDTO> listDevelopers(); // Return a list of developers

  // List of both developers and testers
  List<UserDTO> listDevelopersAndTesters(); // Return a combined list of developers and testers

  // List of all users, only admin privilege -- DONE
  UserApiResponse listAllUsers();

  // Changing details of user including password, email, role
  UserApiResponse updateUser(Integer uniqueId, UserDTO userDetails); // Update user details like password, email, role

}
