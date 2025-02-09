package com.cdac.bugbridge.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.cdac.bugbridge.dto.BugDTO;
import com.cdac.bugbridge.models.Bug;
import com.cdac.bugbridge.models.BugAssignment;
import com.cdac.bugbridge.models.User;
import com.cdac.bugbridge.repository.BugRepository;
import com.cdac.bugbridge.repository.UserRepository;
import com.cdac.bugbridge.response.BugApiResponse;
import com.cdac.bugbridge.response.BugAssignmentApiResponse;
import com.cdac.bugbridge.service.BugService;
import com.cdac.bugbridge.util.UserRole;

@RestController
@RequestMapping("/api/bugs")
public class BugController {

  @Autowired
  UserRepository userRepository;
  @Autowired
  BugRepository bugRepository;
  private final BugService bugService;

  public BugController(BugService bugService) {
    this.bugService = bugService;
  }

  @PostMapping("/create")
  public ResponseEntity<BugApiResponse> createBug(@RequestBody BugDTO request) {
    BugApiResponse createdBugResponse = bugService.createBug(
        request.getReportedBy(),
        request.getAssignedTo(),
        request.getDescription(),
        request.getPriority());
    return ResponseEntity.status(HttpStatus.CREATED).body(createdBugResponse);
  }

  // List all new Bug -- // --DONE
  @GetMapping("/allBugs")
  public ResponseEntity<BugApiResponse> getAllBugs(
      @RequestParam(name = "user_id", required = false) Long id) {
    if (id != null) {
      BugApiResponse response = bugService.findByAssignedToId(id);
      return ResponseEntity.ok(response);
    }
    BugApiResponse response = bugService.findAll();
    return ResponseEntity.ok(response);
  }

  // bug by bug_id -- DONE
  @GetMapping(value = "/{id}")
  public ResponseEntity<BugApiResponse> getBugById(@PathVariable Long id) {
    if (id != null) {
      BugApiResponse response = bugService.findBugById(id);
      return ResponseEntity.ok(response);
    }
    return ResponseEntity.ok(new BugApiResponse(403, "GET Id Not provided", "/api/bugs"));
  }

  @PutMapping("/delete/{id}")
  public ResponseEntity<BugApiResponse> softDeleteBug(@PathVariable Long id) {
    if (id == null) {
      return ResponseEntity.status(HttpStatus.BAD_REQUEST)
          .body(new BugApiResponse(400, "Bug ID must be provided", "/api/bugs/delete"));
    }
    BugApiResponse response = bugService.softDeleteBug(id);
    return ResponseEntity.ok(response);
  }

  // delete a bug // ADMIN
  @DeleteMapping("/{id}")
  public ResponseEntity<BugApiResponse> deleteBug(@PathVariable Long id) {
    if (id != null) {
      BugApiResponse response = bugService.deleteBug(id);
      return ResponseEntity.ok(response);
    }
    return ResponseEntity.ok(new BugApiResponse(403, "DELETE Id Not provided", "/api/bugs"));
  }

  @PatchMapping("/{id}")
  public ResponseEntity<BugApiResponse> updateBug(@RequestBody BugDTO bugDTO, @PathVariable Long id) {
    BugApiResponse response = bugService.updateBug(bugDTO, id);
    return ResponseEntity.ok(response);
  }

  @GetMapping("/test/{id}")
  public ResponseEntity<BugAssignmentApiResponse> get(@PathVariable Long id) {
    User user = userRepository.findById(id)
        .orElseThrow(() -> new RuntimeException("Reporter not found"));
    List<BugAssignment> lis = user.getAssignedBugAssignments();

    BugAssignmentApiResponse response = new BugAssignmentApiResponse(lis);
    return ResponseEntity.ok(response);
  }

  @GetMapping("/testB/{id}")
  public ResponseEntity<BugApiResponse> getBugs(@PathVariable Long id) {
    User user = userRepository.findById(id)
        .orElseThrow(() -> new RuntimeException("Reporter not found"));
    List<Bug> lis = user.getReportedBugs();
    List<BugDTO> list = new ArrayList<>();
    for (Bug bug : lis) {
      BugDTO bugDTO = new BugDTO();
      bugDTO = bugService.mapBugToDTO(bug);
      list.add(bugDTO);
    }
    BugApiResponse response = new BugApiResponse(200, "Fetched", "/api/bugs/test", list);
    return ResponseEntity.ok(response);
  }

  // Get bugs assigned to a developer by their ID
  @GetMapping("/assigned/{developerId}")
  public ResponseEntity<BugApiResponse> getAssignedBugs(@PathVariable Long developerId) {
    // Check if the user exists and is a developer
    Optional<User> userOptional = userRepository.findById(developerId);

    if (userOptional.isEmpty()) {
      return ResponseEntity.status(404).body(null);
    }

    User user = userOptional.get();

    if (user.getRole() != UserRole.DEVELOPER) {
      return ResponseEntity.status(403).body(null);
    }

    // Fetch the bugs assigned to this developer
    List<Bug> assignedBugs = bugRepository.findByAssignedTo(user);

    if (assignedBugs.isEmpty()) {
      return ResponseEntity.status(404).body(null);
    }
    List<BugDTO> list = new ArrayList<>();
    for (Bug bug : assignedBugs) {
      BugDTO bugDTO = new BugDTO();
      bugDTO = bugService.mapBugToDTO(bug);
      list.add(bugDTO);

    }
    return ResponseEntity.ok(new BugApiResponse(200, "Fetched", "/api/bugs/assigned", list));
  }

}
