package com.cdac.bugbridge.models;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "bug_assignments", uniqueConstraints = {
    @UniqueConstraint(columnNames = { "tester_id", "bug_id" })
})
public class BugAssignment {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne(fetch = FetchType.EAGER)
  @JoinColumn(name = "tester_id", nullable = false)
  private User tester;

  @ManyToOne
  @JoinColumn(name = "bug_id", nullable = false)
  private Bug bug;

  @ManyToOne
  @JoinColumn(name = "developer_id", nullable = false)
  private User developer;

  @Column(name = "assigned_at", nullable = false, updatable = false)
  private LocalDate assignedAt;

  public BugAssignment() {
  }

  public BugAssignment(User tester, Bug bug, User developer) {
    this.tester = tester;
    this.bug = bug;
    this.developer = developer;
    this.assignedAt = LocalDate.now();
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public User getTester() {
    return tester;
  }

  public void setTester(User tester) {
    this.tester = tester;
  }

  public Bug getBug() {
    return bug;
  }

  public void setBug(Bug bug) {
    this.bug = bug;
  }

  public User getDeveloper() {
    return developer;
  }

  public void setDeveloper(User developer) {
    this.developer = developer;
  }

  public LocalDate getAssignedAt() {
    return assignedAt;
  }

}
