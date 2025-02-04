package com.cdac.bugbridge.dao;

import org.springframework.stereotype.Service;

import com.cdac.bugbridge.models.Bug;
import com.cdac.bugbridge.repository.BugRepository;

@Service
public class BugDAOImpl implements BugDAO {

  private final BugRepository bugRepository;

  public BugDAOImpl(BugRepository bugRepository) {
    this.bugRepository = bugRepository;
  }

  // create a bug
  @Override
  public Bug createBug(Bug entityBug) {
    return bugRepository.save(entityBug);
  }

}
