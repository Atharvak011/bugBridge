package com.cdac.bugbridge.dao;

import java.util.List;
import java.util.Optional;

import com.cdac.bugbridge.exception.UserException;
import com.cdac.bugbridge.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cdac.bugbridge.repository.UserRepository;

@Service
public class UserDAOImpl implements UserDAO {

  private final UserRepository userRepository;

  // Constructor
  @Autowired
  public UserDAOImpl(UserRepository userRepository) {
    this.userRepository = userRepository;
  }



  // Registering a new user and saving to the database
  @Override
  public void addUser(User entityUser) throws UserException.UserAlreadyExistsException {
    try{
    userRepository.save(entityUser);
    } catch (Exception ex) {
      throw new UserException.UserAlreadyExistsException("Error: "+ex);
    }
  }




  // finding a user by email id
  @Override
  public Optional<User> findUserByEmail(String emailId)  {
    return userRepository.findByEmail(emailId);
  }

  @Override
  public void deleteUser(String emailId) {
    // TODO Auto-generated method stub


  }

  @Override
  public List<User> listTesters() {
    // TODO Auto-generated method stub
    return null;

  }

  @Override
  public List<User> listDevelopers() {
    // TODO Auto-generated method stub

    return null;
  }

  @Override
  public List<User> listDevelopersAndTesters() {
    // TODO Auto-generated method stub
    return null;

  }


  @Override
  public List<User> listAllUsers() {
    // TODO Auto-generated method stub
    return null;

  }


  @Override
  public void updateUser(String emailId, User userDetails) {
    // TODO Auto-generated method stub

  }



}
