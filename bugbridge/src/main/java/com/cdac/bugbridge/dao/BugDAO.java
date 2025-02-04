package com.cdac.bugbridge.dao;

import java.util.List;

import com.cdac.bugbridge.dto.BugDTO;
import com.cdac.bugbridge.models.Bug;

public interface BugDAO {

  // creating a new Bug -- DONE
  Bug createBug(Bug entityBug);

  List<Bug> findByAssignedToId(Long id);

  List<Bug> findAll();

}
