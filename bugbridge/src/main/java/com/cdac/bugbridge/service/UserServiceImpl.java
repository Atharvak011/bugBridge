package com.cdac.bugbridge.service;

import com.cdac.bugbridge.dao.UserDAO;
import com.cdac.bugbridge.dto.UserDTO;

import com.cdac.bugbridge.exception.UserException;
import com.cdac.bugbridge.models.User;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

  private final UserDAO userDao;

  @Autowired
  public UserServiceImpl(UserDAO userDao) {
    this.userDao = userDao;
  }



  // Registering new user  -- DONE
  @Override
  public void addUser(UserDTO userDTO) throws UserException.UserAlreadyExistsException {
      BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
      String hashedPassword=bCryptPasswordEncoder.encode(userDTO.getPassword());
      User entityUser = new User();
      BeanUtils.copyProperties(userDTO,entityUser);
      entityUser.setPassword(hashedPassword);
      userDao.addUser(entityUser);
  }




  // finding a user by email
  @Override
  public boolean findUserByEmail(UserDTO userDTO) {
    System.out.println("Service:"+userDTO.toString());
    String emailId = userDTO.getEmail();
    Optional<User> userByEmail = userDao.findUserByEmail(emailId);
    if(userByEmail.isPresent()){
      User user = userByEmail.get();
      String hashedPassword=user.getPassword();
      BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
      return bCryptPasswordEncoder.matches(userDTO.getPassword(),hashedPassword);
    }
    return false;

  }





    // delete a user using email
  @Override
  public void deleteUserByEmail(String emailId) {

  }




  @Override
  public List<UserDTO> listTesters() {
    return List.of();
  }




  @Override
  public List<UserDTO> listDevelopers() {
    return List.of();
  }




  @Override
  public List<UserDTO> listDevelopersAndTesters() {
    return List.of();
  }




  @Override
  public List<UserDTO> listAllUsers() {
    return List.of();
  }






  @Override
  public void updateUser(String emailId, UserDTO userDetails) {

  }



}
