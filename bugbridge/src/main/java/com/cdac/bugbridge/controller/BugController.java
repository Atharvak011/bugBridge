package com.cdac.bugbridge.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.cdac.bugbridge.dto.BugDTO;
import com.cdac.bugbridge.models.Bug;
import com.cdac.bugbridge.models.User;
import com.cdac.bugbridge.repository.UserRepository;
import com.cdac.bugbridge.response.BugApiResponse;
import com.cdac.bugbridge.service.BugService;

@RestController
@RequestMapping("/api/bugs")
public class BugController {

  private final BugService bugService;

  // @Autowired
  public BugController(BugService bugService) {
    this.bugService = bugService;
  }

  // cretae a new Bug
  @PostMapping("/create")
  public ResponseEntity<BugApiResponse> createBug(@RequestBody BugDTO request) {
    System.out.println(request);
    BugApiResponse createdBugResponse = bugService.createBug(
        request.getReportedBy(),
        request.getAssignedTo(),
        request.getDescription(),
        request.getPriority());
    return ResponseEntity.ok(createdBugResponse);
  }

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

  // bug by bug_id
  // @GetMapping(value = "/bugs/{id}")
  // public ResponseEntity getBugById(@PathVariable Long id) {
  // return new ResponseEntity(bugRepository.findById(id), HttpStatus.OK);
  // }

}
