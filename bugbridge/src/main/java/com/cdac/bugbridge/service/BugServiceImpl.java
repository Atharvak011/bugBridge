package com.cdac.bugbridge.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import org.hibernate.Hibernate;
import org.modelmapper.ModelMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cdac.bugbridge.dao.BugDAO;
import com.cdac.bugbridge.dto.BugDTO;
import com.cdac.bugbridge.models.Bug;
import com.cdac.bugbridge.models.BugAssignment;
import com.cdac.bugbridge.models.User;
import com.cdac.bugbridge.repository.BugAssignmentRepository;
import com.cdac.bugbridge.repository.BugRepository;
import com.cdac.bugbridge.repository.UserRepository;
import com.cdac.bugbridge.response.BugApiResponse;
import com.cdac.bugbridge.util.BugPriority;
import com.cdac.bugbridge.util.BugStatus;

import jakarta.transaction.Transactional;

@Service
public class BugServiceImpl implements BugService {
  @Autowired
  private BugRepository bugRepository;

  @Autowired
  private UserRepository userRepository;

  @Autowired
  private BugAssignmentRepository bugAssignmentRepository;

  private final BugDAO bugDAO;

  private final ModelMapper modelMapper;

  @Autowired
  public BugServiceImpl(BugDAO bugDAO, ModelMapper modelMapper) {
    this.modelMapper = modelMapper;
    this.bugDAO = bugDAO;
  }

  @Override
  @Transactional
  public BugApiResponse createBug(Long reporterId, Long developerId, String description, BugPriority priority) {
    // Fetch reporter (tester)
    User reporter = userRepository.findById(reporterId)
        .orElseThrow(() -> new RuntimeException("Reporter not found"));

    User developer = null;
    if (developerId != null) { // ✅ Only fetch if assignedTo is not null
      developer = userRepository.findById(developerId)
          .orElseThrow(() -> new RuntimeException("Developer not found with ID: " + developerId));
    }
    // Create bug
    Bug bug = new Bug(description, priority, reporter, developer, null);

    // // Automatically create a BugAssignment entry
    // BugAssignment assignment = new BugAssignment(reporter, bug, developer);
    // bugAssignmentRepository.save(assignment);

    Bug savedBug = bugDAO.createBug(bug);
    BugDTO bugDTO = modelMapper.map(savedBug, BugDTO.class);
    return new BugApiResponse((savedBug != null ? 201 : 403), (savedBug != null ? "Created" : "Error"), "/api/bugs/",
        bugDTO);
  }

  @Override
  @Transactional
  public BugApiResponse findByAssignedToId(Long id) {
    List<BugDTO> bugDTOList = bugDAO.findByAssignedToId(id).stream().map(bug -> modelMapper.map(bug, BugDTO.class))
        .collect(Collectors.toList());
    return new BugApiResponse(200, "", "/api/bugs", bugDTOList);
  }

  @Override
  @Transactional
  public BugApiResponse findAll() {
    List<Bug> bugList = bugDAO.findAll();

    // // Initialize lazy fields
    // bugList.forEach(bug -> {
    // Hibernate.initialize(bug.getReportedBy());
    // Hibernate.initialize(bug.getAssignedTo());
    // });

    List<BugDTO> bugDTOList = bugList.stream()
        .map(bug -> modelMapper.map(bug, BugDTO.class))
        .collect(Collectors.toList());

    return new BugApiResponse(200, "", "/api/bugs", bugDTOList);
  }

}
