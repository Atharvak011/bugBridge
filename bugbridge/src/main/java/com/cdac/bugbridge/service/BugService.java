package com.cdac.bugbridge.service;

import com.cdac.bugbridge.dto.BugDTO;
import com.cdac.bugbridge.models.Bug;
import com.cdac.bugbridge.response.BugApiResponse;

public interface BugService {

  // creating a bug
  BugApiResponse createBug(Long reportedBy, Long assignedTo, String description, String priority);

  // list of all the bugs assigned by the tester
  BugApiResponse findByAssignedToId(Long id);

  // list of all bugs in the db
  BugApiResponse findAll();

  // bug find by bug_id
  BugApiResponse findBugById(Long id);

  // delete a bug
  BugApiResponse deleteBug(Long id);

  // update a bug
  BugApiResponse updateBug(BugDTO bugDTO, Long id);

  BugDTO mapBugToDTO(Bug bug);

  BugApiResponse softDeleteBug(Long id);

}
