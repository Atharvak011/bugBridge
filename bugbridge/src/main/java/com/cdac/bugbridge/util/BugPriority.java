package com.cdac.bugbridge.util;

public enum BugPriority {
  LOW,
  MEDIUM,
  HIGH;

  public static BugPriority fromString(String priority) {
    try {
      if (priority == null)
        return null;
      return BugPriority.valueOf(priority.trim().toUpperCase()); // Convert to uppercase
    } catch (IllegalArgumentException e) {
      throw new RuntimeException("Invalid role: " + priority);
    }
  }

  public static String toStringFromEnum(BugPriority priority) {
    try {
      return priority.name();
    } catch (IllegalArgumentException e) {
      throw new RuntimeException("Invalid role: " + priority);
    }
  }
}
