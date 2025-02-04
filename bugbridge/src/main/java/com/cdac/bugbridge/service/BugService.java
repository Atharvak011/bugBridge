package com.cdac.bugbridge.service;

import com.cdac.bugbridge.dto.BugDTO;
import com.cdac.bugbridge.models.Bug;
import com.cdac.bugbridge.models.User;
import com.cdac.bugbridge.response.BugApiResponse;
import com.cdac.bugbridge.util.BugPriority;

public interface BugService {

  BugApiResponse createBug(Long reportedBy, Long assignedTo, String description, BugPriority priority);

}
