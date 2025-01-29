package com.cdac.bugbridge.dao;

import com.cdac.bugbridge.exception.UserException;
import com.cdac.bugbridge.models.User;

import java.util.List;
import java.util.Optional;

public interface UserDAO {

  // Add a new user (registration) with Developer and Tester roles only -- DONE
  void addUser(User user) throws UserException.UserAlreadyExistsException; // Only Developer and Tester roles allowed

  // finding user by email --  DONE
  Optional<User> findUserByEmail(String emailID);

  // Delete a user by emailId (only admin can delete)
  void deleteUser(String emailId); // Only admin can delete

  // List all testers
  List<User> listTesters(); // Return a list of testers

  // List all developers
  List<User> listDevelopers(); // Return a list of developers

  // List both developers and testers
  List<User> listDevelopersAndTesters(); // Return a combined list of developers and testers

  // List all users (accessible only to admin)
  List<User> listAllUsers(); // Return a list of all users

  // Update user details (password, email, role)
  void updateUser(String emailId, User userDetails); // Admin or specific users can update details


}
