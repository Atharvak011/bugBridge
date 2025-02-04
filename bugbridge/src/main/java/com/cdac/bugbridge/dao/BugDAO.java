package com.cdac.bugbridge.dao;

import java.util.List;
import java.util.Optional;

import com.cdac.bugbridge.dto.BugDTO;
import com.cdac.bugbridge.models.Bug;

public interface BugDAO {

  // creating a new Bug -- DONE
  Bug createBug(Bug entityBug);

  List<Bug> findByAssignedToId(Long id);

  List<Bug> findAll();

  Optional<Bug> findBugById(Long id);

}
