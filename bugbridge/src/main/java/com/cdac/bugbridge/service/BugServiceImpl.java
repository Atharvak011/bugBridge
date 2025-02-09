package com.cdac.bugbridge.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
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

  public BugServiceImpl(BugDAO bugDAO, ModelMapper modelMapper) {
    this.modelMapper = modelMapper;
    this.bugDAO = bugDAO;
  }

  @Override
  @Transactional
  public BugApiResponse createBug(Long reporterId, Long developerId, String description, String priority) {
    // Fetch reporter (tester)
    User reporter = userRepository.findById(reporterId)
        .orElseThrow(() -> new RuntimeException("Reporter not found"));

    User developer = null;
    if (developerId != null) {
      developer = userRepository.findById(developerId)
          .orElseThrow(() -> new RuntimeException("Developer not found with ID: " + developerId));
    }
    // Create bug
    Bug bug = new Bug(description, BugPriority.fromString(priority), reporter, developer, null);
    Bug savedBug = bugDAO.createBug(bug);

    if (savedBug == null || savedBug.getId() == null) { // Check if Bug is really saved
      return new BugApiResponse(500, "Bug not saved", "/api/bugs");
    }
    BugDTO bugDTO = modelMapper.map(savedBug, BugDTO.class);
    return new BugApiResponse(201, "Created", "/api/bugs/", bugDTO);
  }

  @Override
  @Transactional
  public BugApiResponse findByAssignedToId(Long id) {
    List<BugDTO> bugDTOList = bugDAO.findByAssignedToId(id)
        .stream()
        .map(bug -> mapBugToDTO(bug))
        .collect(Collectors.toList());
    return new BugApiResponse(200, "Bug by provided id: " + id, "/api/bugs", bugDTOList);
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

    List<BugDTO> bugDTOList = bugList
        .stream()
        .map(bug -> mapBugToDTO(bug))
        .collect(Collectors.toList());

    return new BugApiResponse(200, "List of All bugs in Db", "/api/bugs", bugDTOList);
  }

  @Override
  @Transactional
  public BugApiResponse findBugById(Long id) {
    BugDTO bugDTO = bugDAO.findBugById(id).map(bug -> mapBugToDTO(bug))
        .orElse(null);
    return bugDTO != null ? new BugApiResponse(200, "Found Bug id: " + id, "/api/bugs", bugDTO)
        : new BugApiResponse(200, "Bug Not Found", "/api/bugs", bugDTO);

  }

  @Override
  @Transactional
  public BugApiResponse deleteBug(Long id) {
    BugDTO bugDTO = bugDAO.deleteBug(id).map(bug -> mapBugToDTO(bug))
        .orElse(null);
    return bugDTO != null ? new BugApiResponse(200, "Bug Deleted Success id: " + id, "/api/bugs", bugDTO)
        : new BugApiResponse(200, "Bug Not Found id: " + id, "/api/bugs", bugDTO);

  }

  @Override
  @Transactional
  public BugApiResponse updateBug(BugDTO bugDTO, Long id) {
    // Retrieve bug from database
    Optional<Bug> existingBugOptional = bugRepository.findById(id);
    if (!existingBugOptional.isPresent()) {
      return new BugApiResponse(404, "Bug Not Found", "/api/bugs/", bugDTO);
    }

    Bug existingBug = existingBugOptional.get();

    User oldAssignedTo = existingBug.getAssignedTo();

    if (bugDTO.getDescription() != null) {
      existingBug.setDescription(bugDTO.getDescription());
    }
    if (bugDTO.getPriority() != null) {
      existingBug.setPriority(BugPriority.fromString(bugDTO.getPriority()));
    }
    if (bugDTO.getAssignedTo() != null) {
      User developer = userRepository.findById(bugDTO.getAssignedTo()).orElse(null);
      existingBug.setAssignedTo(developer);
    }
    if (bugDTO.getStatus() != null) {
      existingBug.setStatus(BugStatus.fromString(bugDTO.getStatus()));
    }
    if (bugDTO.getResolutionReport() != null) {
      existingBug.setResolutionReport(bugDTO.getResolutionReport());
    }
    if (bugDTO.getResolvedAt() != null) {
      existingBug.setResolvedAt(bugDTO.getResolvedAt());
    }
    if (bugDTO.getIsDeleted() != null) {
      existingBug.setIsDeleted(bugDTO.getIsDeleted());
    }

    Bug savedBug = bugDAO.updateBug(existingBug);

    if (bugDTO.getAssignedTo() == null) {

    }

    if ((oldAssignedTo == null || !oldAssignedTo.equals(savedBug.getAssignedTo()))
        && savedBug.getAssignedTo() != null) {
      User reporter = savedBug.getReportedBy();
      User developer = savedBug.getAssignedTo();
      BugAssignment assignment = new BugAssignment(reporter, savedBug, developer);
      assignment.setBug(savedBug);
      bugAssignmentRepository.save(assignment);
    }

    BugDTO bugDTOresponse = mapBugToDTO(savedBug);

    return new BugApiResponse(200, "Bug Updated", "/api/bugs/", bugDTOresponse);
  }

  // --------------------------------------------------------------------------------
  @Override
  public BugApiResponse softDeleteBug(Long id) {
    Bug bug = bugDAO.findBugById(id)
        .orElse(null);
    if (bug == null) {
      return new BugApiResponse(404, "Bug Not Found", "/api/bugs/");
    }
    bug.setIsDeleted(true);
    bug.setStatus(BugStatus.RESOLVED);
    Bug savedBug = bugDAO.updateBug(bug);
    BugDTO bugDTO = mapBugToDTO(savedBug);
    return new BugApiResponse(200, "Bug Soft Deleted", "/api/bugs/", bugDTO);
  }

  // mapping DTO
  @Override
  public BugDTO mapBugToDTO(Bug bug) {
    BugDTO bugDTO = new BugDTO();
    bugDTO.setId(bug.getId());
    bugDTO.setDateReported(bug.getDateReported());
    bugDTO.setDescription(bug.getDescription());
    bugDTO.setPriority(bug.getPriority().toString());
    bugDTO.setReportedBy(bug.getReportedBy() != null ? bug.getReportedBy().getId() : null);
    bugDTO.setAssignedTo(bug.getAssignedTo() != null ? bug.getAssignedTo().getId() : null);
    bugDTO.setStatus(bug.getStatus().toString());
    bugDTO.setResolutionReport(bug.getResolutionReport());
    bugDTO.setResolvedAt(bug.getResolvedAt());
    bugDTO.setIsDeleted(bug.getIsDeleted());
    return bugDTO;
  }
}
