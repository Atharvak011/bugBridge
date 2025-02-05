package com.cdac.bugbridge.dao;

import java.util.List;
import java.util.Optional;

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

  @Override
  public List<Bug> findByAssignedToId(Long id) {
    List<Bug> list = bugRepository.findByReportedById(id);
    list.forEach(System.out::println);
    return list;
  }

  @Override
  public List<Bug> findAll() {
    return bugRepository.findAll();
  }

  @Override
  public Optional<Bug> findBugById(Long id) {
    Optional<Bug> bug = bugRepository.findById(id);
    return bug;
  }

  @Override
  public Optional<Bug> deleteBug(Long id) {
    Optional<Bug> bug = bugRepository.findById(id);
    if (bug.isPresent()) {
      bugRepository.deleteById(id);
    }
    return bug;
  }

  @Override
  public Bug updateBug(Bug bug) {
    return bugRepository.save(bug);
  }

}
