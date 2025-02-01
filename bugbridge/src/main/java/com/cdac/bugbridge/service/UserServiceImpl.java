package com.cdac.bugbridge.service;

import com.cdac.bugbridge.dao.UserDAO;
import com.cdac.bugbridge.dto.UserDTO;
import com.cdac.bugbridge.dto.UserResponse;
import com.cdac.bugbridge.exception.UserException;
import com.cdac.bugbridge.models.User;
import com.cdac.bugbridge.response.UserApiResponse;
import com.cdac.bugbridge.util.UserRole;

import jakarta.transaction.Transactional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

  private final UserDAO userDao;

  private final ModelMapper modelMapper;

  @Autowired
  public UserServiceImpl(UserDAO userDao, ModelMapper modelMapper) {
    this.userDao = userDao;
    this.modelMapper = modelMapper;
  }

  // Registering new user -- DONE
  @Override
  public void addUser(UserDTO userDTO) throws UserException.UserAlreadyExistsException {
    BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
    String hashedPassword = bCryptPasswordEncoder.encode(userDTO.getPassword());
    User entityUser = new User();
    BeanUtils.copyProperties(userDTO, entityUser);
    entityUser.setPassword(hashedPassword);
    userDao.addUser(entityUser);
  }

  // finding a user by email
  @Override
  public boolean findUserByEmail(UserDTO userDTO) {
    String emailId = userDTO.getEmail();
    Optional<User> userByEmail = userDao.findUserByEmail(emailId);
    if (userByEmail.isPresent()) {
      User user = userByEmail.get();
      String hashedPassword = user.getPassword();
      BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
      return bCryptPasswordEncoder.matches(userDTO.getPassword(), hashedPassword);
    }
    return false;

  }

  // finding a user by User id
  @Override
  public boolean findUserById(UserDTO userDTO) {
    Integer userId = userDTO.getId();
    Optional<User> userByEmail = userDao.findUserById(userId);
    if (userByEmail.isPresent()) {
      User user = userByEmail.get();
      String hashedPassword = user.getPassword();
      BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
      return bCryptPasswordEncoder.matches(userDTO.getPassword(), hashedPassword);
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
  public UserApiResponse listAllUsers() {
    List<UserResponse> userList = userDao.listAllUsers()
        .stream()
        .map(user -> modelMapper.map(user, UserResponse.class))
        .collect(Collectors.toList());
    return new UserApiResponse(200, "List of All users", "/api/admin/users", null, userList);
  }

  @Override
  @Transactional
  public UserApiResponse updateUser(Integer userId, UserDTO userDTO) {
    // Optional<User> userByEmail = userDao.findUserById(userId);
    // if (userByEmail.isPresent()) {
    // User user = userByEmail.get();
    // // Handle null 'role' using Optional
    // Optional.ofNullable(userDTO.getRole())
    // .map(String::trim) // Trim and check if not empty
    // .filter(role -> !role.isEmpty()) // Only set if not empty
    // .ifPresent(role -> user.setRole(role.toUpperCase()));
    // // Manually copy other properties
    // Optional.ofNullable(userDTO.getName()).ifPresent(user::setName);
    // Optional.ofNullable(userDTO.getEmail()).ifPresent(user::setEmail);
    // int rowsAffected = userDao.updateUser(userId, user);
    // UserApiResponse response = rowsAffected > 0
    // ? new UserApiResponse(201, "User Details Updated",
    // "api/authorized/profileInfo")
    // : new UserApiResponse(403, "User Details Not Updated",
    // "api/authorized/profileInfo");
    // return response;
    // }
    // return new UserApiResponse(404, "User Not Found",
    // "api/authorized/profileInfo");
    return userDao.findUserById(userId)
        .map(user -> {
          Optional.ofNullable(userDTO.getRole())
              .map(String::trim)
              .filter(role -> !role.isEmpty())
              .ifPresent(user::setRole);
          // .ifPresent(role -> user.setRole(role.toUpperCase()));
          Optional.ofNullable(userDTO.getName()).ifPresent(user::setName);
          Optional.ofNullable(userDTO.getEmail()).ifPresent(user::setEmail);

          int rowsAffected = userDao.updateUser(userId, user);
          return rowsAffected > 0
              ? new UserApiResponse(201, "User Details Updated", "api/authorized/profileInfo")
              : new UserApiResponse(403, "User Details Not Updated", "api/authorized/profileInfo");
        })
        .orElse(new UserApiResponse(404, "User Not Found", "api/authorized/profileInfo"));

  }

}
