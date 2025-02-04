package com.cdac.bugbridge.dao;

import com.cdac.bugbridge.exception.UserException;
import com.cdac.bugbridge.models.User;

import java.util.List;
import java.util.Optional;

public interface UserDAO {

  // Add a new user (registration) with Developer and Tester roles only -- DONE
  void addUser(User user) throws UserException.UserAlreadyExistsException; // Only Developer and Tester roles allowed

  // finding user by email -- DONE
  Optional<User> findUserByEmail(String emailID);

  // finding user by id --DONE
  Optional<User> findUserById(Long userId);

  // Delete a user by userId (only admin can delete)
  int deleteUserById(Long userId); // Only admin can delete

  // List all users (accessible only to admin) -- DONE
  List<User> listAllUsers();

  // Update user details (password, newEmail, role)
  int updateUser(Long uniqueId, User userDetails); // Admin or specific users can update details

}
