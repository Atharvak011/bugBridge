package com.cdac.bugbridge.util;

public enum BugStatus {
  OPEN,
  IN_PROGRESS,
  RESOLVED;

  public static BugStatus fromString(String bugStatus) {
    try {
      if (bugStatus == null)
        return null;
      return BugStatus.valueOf(bugStatus.trim().toUpperCase()); // Convert to uppercase
    } catch (IllegalArgumentException e) {
      throw new RuntimeException("Invalid role: " + bugStatus);
    }
  }

  public static String toStringFromEnum(BugStatus bugStatus) {
    try {
      return bugStatus.name();
    } catch (IllegalArgumentException e) {
      throw new RuntimeException("Invalid role: " + bugStatus);
    }
  }
}
