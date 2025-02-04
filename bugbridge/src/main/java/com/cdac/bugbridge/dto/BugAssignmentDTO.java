package com.cdac.bugbridge.dto;

import java.time.LocalDate;

public class BugAssignmentDTO {

  private Long id;
  private String tester; // Tester name or identifier
  private String developer; // Developer name or identifier
  private Long bugId; // ID of the associated bug
  private LocalDate assignedAt;

  public BugAssignmentDTO(Long id, String tester, String developer, Long bugId, LocalDate assignedAt) {
    this.id = id;
    this.tester = tester;
    this.developer = developer;
    this.bugId = bugId;
    this.assignedAt = assignedAt;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getTester() {
    return tester;
  }

  public void setTester(String tester) {
    this.tester = tester;
  }

  public String getDeveloper() {
    return developer;
  }

  public void setDeveloper(String developer) {
    this.developer = developer;
  }

  public Long getBugId() {
    return bugId;
  }

  public void setBugId(Long bugId) {
    this.bugId = bugId;
  }

  public LocalDate getAssignedAt() {
    return assignedAt;
  }

  public void setAssignedAt(LocalDate assignedAt) {
    this.assignedAt = assignedAt;
  }
}
