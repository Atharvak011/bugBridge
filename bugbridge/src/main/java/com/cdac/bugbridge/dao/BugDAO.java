package com.cdac.bugbridge.dao;

import java.util.List;
import java.util.Optional;

import com.cdac.bugbridge.models.Bug;

public interface BugDAO {

  Bug createBug(Bug entityBug);

  List<Bug> findByAssignedToId(Long id);

  List<Bug> findAll();

  Optional<Bug> findBugById(Long id);

  Optional<Bug> deleteBug(Long id);

  Bug updateBug(Bug bug);

}
