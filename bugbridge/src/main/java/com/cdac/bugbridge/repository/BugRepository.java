package com.cdac.bugbridge.repository;

import com.cdac.bugbridge.models.Bug;
import com.cdac.bugbridge.util.BugStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface BugRepository extends JpaRepository<Bug, Long> {
  List<Bug> findByStatus(BugStatus status);
}
