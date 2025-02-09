package com.cdac.bugbridge.util;

public enum UserRole {
  ADMIN,
  DEVELOPER,
  TESTER;

  public static UserRole fromString(String role) {
    try {
      if (role == null)
        return null;
      return UserRole.valueOf(role.trim().toUpperCase());
    } catch (IllegalArgumentException e) {
      throw new RuntimeException("Invalid role: " + role);
    }
  }

  public static String toStringFromEnum(UserRole role) {
    try {
      return role.name();
    } catch (IllegalArgumentException e) {
      throw new RuntimeException("Invalid role: " + role);
    }
  }

}
