package com.cdac.bugbridge.service;

import com.cdac.bugbridge.dto.BugDTO;
import com.cdac.bugbridge.models.Bug;
import com.cdac.bugbridge.models.User;
import com.cdac.bugbridge.response.BugApiResponse;
import com.cdac.bugbridge.util.BugPriority;

public interface BugService {

  // creating a bug
  BugApiResponse createBug(Long reportedBy, Long assignedTo, String description, BugPriority priority);

  // list of all the bugs assigned by the tester
  BugApiResponse findByAssignedToId(Long id);

  // list of all bugs in the db
  BugApiResponse findAll();

  // bug find by bug_id

  BugApiResponse findBugById(Long id);

}
