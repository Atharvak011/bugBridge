package com.cdac.bugbridge.models;

import com.cdac.bugbridge.util.BugPriority;
import com.cdac.bugbridge.util.BugStatus;
import com.fasterxml.jackson.annotation.JsonFormat;
// import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude;

import jakarta.persistence.*;

import java.time.LocalDate;
// import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@JsonInclude(JsonInclude.Include.NON_NULL)
@Table(name = "bugs")
public class Bug {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  // @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
  // @Column(name = "date_reported", nullable = false, updatable = false)
  // private LocalDateTime dateReported = LocalDateTime.now();

  @JsonFormat(pattern = "yyyy-MM-dd")
  @Column(name = "date_reported", nullable = false, updatable = false)
  private LocalDate dateReported = LocalDate.now();

  @Column(name = "description", nullable = false, columnDefinition = "TEXT")
  private String description;

  @Enumerated(EnumType.STRING)
  @Column(name = "priority", nullable = false)
  private BugPriority priority;

  @ManyToOne(fetch = FetchType.EAGER)
  @JoinColumn(name = "reported_by", nullable = false)
  private User reportedBy;

  @ManyToOne(fetch = FetchType.EAGER)
  @JoinColumn(name = "assigned_to")
  private User assignedTo;

  @Enumerated(EnumType.STRING)
  @Column(name = "status", nullable = false)
  private BugStatus status = BugStatus.OPEN;

  @Column(name = "resolution_report", columnDefinition = "TEXT")
  private String resolutionReport;

  @Column(name = "is_deleted", nullable = false)
  private Boolean isDeleted = false;

  @JsonFormat(pattern = "yyyy-MM-dd")
  @Column(name = "resolved_at")
  private LocalDate resolvedAt;

  @OneToMany(mappedBy = "bug", cascade = CascadeType.ALL, orphanRemoval = true)
  private List<BugAssignment> bugAssignments = new ArrayList<>();

  public Bug() {
  }

  public Bug(String description, BugPriority priority, User reportedBy, User assignedTo, LocalDate resolvedAt) {
    this.description = description;
    this.priority = priority;
    this.reportedBy = reportedBy;
    this.assignedTo = assignedTo;
    this.status = BugStatus.OPEN;
    this.resolvedAt = resolvedAt;
  }

  // Getters and Setters

  public Bug(User developer) {
    //TODO Auto-generated constructor stub
  }

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

  public User getReportedBy() {
    return reportedBy;
  }

  public void setReportedBy(User reportedBy) {
    this.reportedBy = reportedBy;
  }

  public User getAssignedTo() {
    return assignedTo;
  }

  public void setAssignedTo(User assignedTo) {
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
        ", reportedBy=" + (reportedBy != null ? reportedBy.getName() : "N/A") +
        ", assignedTo=" + (assignedTo != null ? assignedTo.getName() : "N/A") +
        ", status=" + status +
        ", resolutionReport='" + resolutionReport + '\'' +
        ", isDeleted=" + isDeleted +
        ", resolvedAt=" + resolvedAt +
        '}';
  }
}
