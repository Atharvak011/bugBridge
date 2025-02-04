package com.cdac.bugbridge.repository;

import com.cdac.bugbridge.models.BugAssignment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BugAssignmentRepository extends JpaRepository<BugAssignment, Long> {

}
