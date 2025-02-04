package com.cdac.bugbridge.dto;

import com.cdac.bugbridge.util.BugPriority;
import com.cdac.bugbridge.util.BugStatus;
import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public class BugDTO {

  private Long id;

  @JsonFormat(pattern = "yyyy-MM-dd")
  private LocalDate dateReported = LocalDate.now();

  @NotNull(message = "Description is required")
  private String description;

  @NotNull(message = "Priority is required")
  private BugPriority priority;

  @NotNull(message = "Reported by is required")
  private Long reportedBy;
  private Long assignedTo;

  @NotNull(message = "Status is required")
  private BugStatus status;
  private String resolutionReport;
  private Boolean isDeleted;

  @JsonFormat(pattern = "yyyy-MM-dd")
  private LocalDate resolvedAt;

  // Constructors
  public BugDTO() {
  }

  public BugDTO(Long id, String description,
      BugPriority priority, Long reportedBy,
      Long assignedTo, BugStatus status,
      String resolutionReport, Boolean isDeleted, LocalDate resolvedAt) {
    this.id = id;
    this.description = description;
    this.priority = priority;
    this.reportedBy = reportedBy;
    this.assignedTo = assignedTo;
    this.status = status;
    this.resolutionReport = resolutionReport;
    this.isDeleted = isDeleted;
    this.resolvedAt = resolvedAt;
  }

  // Getters and Setters
  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public LocalDate getDateReported() {
    return dateReported;
  }

  public void setDateReported(LocalDate dateReported) {
    this.dateReported = dateReported;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public BugPriority getPriority() {
    return priority;
  }

  public void setPriority(BugPriority priority) {
    this.priority = priority;
  }

  public Long getReportedBy() {
    return reportedBy;
  }

  public void setReportedBy(Long reportedBy) {
    this.reportedBy = reportedBy;
  }

  public Long getAssignedTo() {
    return assignedTo;
  }

  public void setAssignedTo(Long assignedTo) {
    this.assignedTo = assignedTo;
  }

  public BugStatus getStatus() {
    return status;
  }

  public void setStatus(BugStatus status) {
    this.status = status;
  }

  public String getResolutionReport() {
    return resolutionReport;
  }

  public void setResolutionReport(String resolutionReport) {
    this.resolutionReport = resolutionReport;
  }

  public Boolean getIsDeleted() {
    return isDeleted;
  }

  public void setIsDeleted(Boolean isDeleted) {
    this.isDeleted = isDeleted;
  }

  public LocalDate getResolvedAt() {
    return resolvedAt;
  }

  public void setResolvedAt(LocalDate resolvedAt) {
    this.resolvedAt = resolvedAt;
  }

  @Override
  public String toString() {
    return "Bug{" +
        "id=" + id +
        // ", version=" + version +
        ", dateReported=" + dateReported +
        ", description='" + description + '\'' +
        ", priority=" + priority +
        ", reportedBy=" + reportedBy +
        ", assignedTo=" + assignedTo +
        ", status=" + status +
        ", resolutionReport='" + resolutionReport + '\'' +
        ", isDeleted=" + isDeleted +
        ", resolvedAt=" + resolvedAt +
        '}';
  }
}
