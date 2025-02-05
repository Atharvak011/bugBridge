package com.cdac.bugbridge.service;

import com.cdac.bugbridge.dao.UserDAO;
import com.cdac.bugbridge.dto.UserDTO;
import com.cdac.bugbridge.dto.UserResponse;
import com.cdac.bugbridge.exception.UserException;
import com.cdac.bugbridge.exception.UserException.UserNotFoundException;
import com.cdac.bugbridge.models.User;
import com.cdac.bugbridge.repository.UserRepository;
import com.cdac.bugbridge.response.UserApiResponse;
import com.cdac.bugbridge.util.UserRole;

import jakarta.transaction.Transactional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

  private final UserDAO userDao;

  private final ModelMapper modelMapper;

  public UserServiceImpl(UserDAO userDao, ModelMapper modelMapper) {
    this.userDao = userDao;
    this.modelMapper = modelMapper;
  }

  // -- DONE
  @Override
  public void addUser(UserDTO userDTO) throws UserException.UserAlreadyExistsException {
    BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
    String hashedPassword = bCryptPasswordEncoder.encode(userDTO.getPassword());
    User entityUser = new User();
    BeanUtils.copyProperties(userDTO, entityUser);
    entityUser.setPassword(hashedPassword);
    userDao.addUser(entityUser);
  }

  // -- DONE
  @Override
  public UserApiResponse findUserById(Long userId) {
    UserResponse responseUser = new UserResponse();
    Optional<User> userByEmail = userDao.findUserById(userId);
    if (userByEmail.isPresent()) {
      User user = userByEmail.get();
      responseUser = modelMapper.map(user, UserResponse.class);
      return new UserApiResponse(200, "User found", "/api/users/dashboard", responseUser, null);
    }
    return new UserApiResponse(200, "User Not found", "/api/users/dashboard", null, null);
  }

  // -- DONE for login check
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

  @Override
  public boolean findUserById(UserDTO userDTO) {
    Long userId = userDTO.getId();
    Optional<User> userByEmail = userDao.findUserById(userId);
    if (userByEmail.isPresent()) {
      User user = userByEmail.get();
      String hashedPassword = user.getPassword();
      BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
      return bCryptPasswordEncoder.matches(userDTO.getPassword(), hashedPassword);
    }
    return false;
  }

  @Override
  @Transactional
  public void deleteUserByEmail(String emailId) {

  }

  @Override
  @Transactional
  public UserApiResponse deleteUserById(Long userId) {
    int rowsAffected = userDao.deleteUserById(userId);
    return rowsAffected > 0 ? new UserApiResponse(201, "Acount Deletion Sucessful", "api/users/allUsers/dashboard")
        : new UserApiResponse(402, "Account Not Registered", "api/users/allUsers/dashboard");
  }

  @Override
  public UserApiResponse listByRole(String role) {
    List<User> userList = userDao.listAllUsers();
    List<UserResponse> filteredResponses = userList.stream()
        .filter(user -> role.equalsIgnoreCase("all")
            ? !user.getRole().equals(UserRole.ADMIN)
            : user.getRole().name().equalsIgnoreCase(role))
        .map(user -> modelMapper.map(user, UserResponse.class))
        .collect(Collectors.toList());
    return new UserApiResponse(200, "List of All " + (role.equalsIgnoreCase("all") ? "Users" : role), "/api/users",
        null,
        filteredResponses);
  }

  @Override
  public UserApiResponse listAllUsers() {
    List<UserResponse> userList = userDao.listAllUsers()
        .stream()
        .map(user -> modelMapper.map(user, UserResponse.class))
        .collect(Collectors.toList());
    return new UserApiResponse(200, "List of All users", "/api/users/admin/users", null, userList);
  }

  @Override
  @Transactional
  public UserApiResponse updateUser(Long userId, UserDTO userDTO) {
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
              ? new UserApiResponse(201, "User Details Updated", "api/users/profileInfo")
              : new UserApiResponse(403, "User Details Not Updated", "api/users/profileInfo");
        })
        .orElse(new UserApiResponse(404, "User Not Found", "api/users/profileInfo"));

  }

  // Omkar Modifing the Code

 

      

  


}
