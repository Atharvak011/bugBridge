package com.cdac.bugbridge.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import java.time.LocalDate;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class BugAssignmentResponse {

  private Long id;
  private String tester;
  private String developer;
  private Long bugId;
  private LocalDate assignedAt;

  public BugAssignmentResponse() {
  }

  public BugAssignmentResponse(Long id, String tester, String developer, Long bugId, LocalDate assignedAt) {
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

  @Override
  public String toString() {
    return "BugAssignmentResponse{" +
        "id=" + id +
        ", tester='" + tester + '\'' +
        ", developer='" + developer + '\'' +
        ", bugId=" + bugId +
        ", assignedAt=" + assignedAt +
        '}';
  }
}
