// package com.cdac.bugbridge.controller;

// import com.cdac.bugbridge.dto.BugAssignmentDTO;
// import com.cdac.bugbridge.models.BugAssignment;
// import com.cdac.bugbridge.repository.BugAssignmentRepository;
// import com.cdac.bugbridge.response.BugAssignmentApiResponse;
// import com.cdac.bugbridge.models.User;
// import com.cdac.bugbridge.repository.UserRepository;
// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.http.ResponseEntity;
// import org.springframework.web.bind.annotation.*;

// import java.util.List;
// import java.util.stream.Collectors;

// @RestController
// @RequestMapping("/api/bug-assignments")
// public class BugAssignmentController {

// private final BugAssignmentRepository bugAssignmentRepository;
// private final UserRepository userRepository;

// @Autowired
// public BugAssignmentController(BugAssignmentRepository
// bugAssignmentRepository, UserRepository userRepository) {
// this.bugAssignmentRepository = bugAssignmentRepository;
// this.userRepository = userRepository;
// }

// // Create a new Bug Assignment
// @PostMapping("/create")
// public ResponseEntity<BugAssignmentApiResponse>
// createBugAssignment(@RequestBody BugAssignmentDTO bugAssignmentDTO) {
// // Fetching the associated tester and developer by their IDs
// User tester = userRepository.findById(bugAssignmentDTO.getId()).orElse(null);
// User developer =
// userRepository.findById(bugAssignmentDTO.getId()).orElse(null);

// if (tester != null && developer != null) {
// BugAssignment bugAssignment = new BugAssignment(tester,
// bugAssignmentDTO.getId(), developer);
// bugAssignmentRepository.save(bugAssignment);

// BugAssignmentDTO dto = new BugAssignmentDTO(
// bugAssignment.getId(),
// tester.getName(),
// developer.getName(),
// bugAssignment.getBug().getId(),
// bugAssignment.getAssignedAt());

// return ResponseEntity.ok(new BugAssignmentApiResponse(200, "Created
// Successfully", "/api/bug-assignments", dto));
// } else {
// return ResponseEntity.status(400)
// .body(new BugAssignmentApiResponse(400, "Invalid Tester or Developer",
// "/api/bug-assignments"));
// }
// }

// // Get a list of bug assignments by tester ID
// @GetMapping("/by-tester/{testerId}")
// public ResponseEntity<BugAssignmentApiResponse>
// getBugAssignmentsByTester(@PathVariable Long testerId) {
// List<BugAssignment> bugAssignments =
// bugAssignmentRepository.findByTesterId(testerId);
// List<BugAssignmentDTO> bugAssignmentDTOs = bugAssignments.stream()
// .map(this::mapToDTO)
// .collect(Collectors.toList());

// return ResponseEntity.ok(new BugAssignmentApiResponse(bugAssignmentDTOs));
// }

// // Get all bug assignments
// @GetMapping("/all")
// public ResponseEntity<BugAssignmentApiResponse> getAllBugAssignments() {
// List<BugAssignment> bugAssignments = bugAssignmentRepository.findAll();
// List<BugAssignmentDTO> bugAssignmentDTOs = bugAssignments.stream()
// .map(this::mapToDTO)
// .collect(Collectors.toList());

// return ResponseEntity.ok(new BugAssignmentApiResponse(bugAssignmentDTOs));
// }

// // Get a bug assignment by ID
// @GetMapping("/{id}")
// public ResponseEntity<BugAssignmentApiResponse>
// getBugAssignmentById(@PathVariable Long id) {
// BugAssignment bugAssignment =
// bugAssignmentRepository.findById(id).orElse(null);
// if (bugAssignment != null) {
// BugAssignmentDTO dto = mapToDTO(bugAssignment);
// return ResponseEntity.ok(new BugAssignmentApiResponse(200, "Fetched
// Successfully", "/api/bug-assignments", dto));
// }
// return ResponseEntity.status(404)
// .body(new BugAssignmentApiResponse(404, "Bug Assignment Not Found",
// "/api/bug-assignments"));
// }

// // Delete a bug assignment by ID
// @DeleteMapping("/{id}")
// public ResponseEntity<BugAssignmentApiResponse>
// deleteBugAssignment(@PathVariable Long id) {
// if (bugAssignmentRepository.existsById(id)) {
// bugAssignmentRepository.deleteById(id);
// return ResponseEntity.ok(new BugAssignmentApiResponse(200, "Deleted
// Successfully", "/api/bug-assignments"));
// }
// return ResponseEntity.status(404)
// .body(new BugAssignmentApiResponse(404, "Bug Assignment Not Found",
// "/api/bug-assignments"));
// }

// // Update a bug assignment
// @PatchMapping("/{id}")
// public ResponseEntity<BugAssignmentApiResponse>
// updateBugAssignment(@RequestBody BugAssignmentDTO bugAssignmentDTO,
// @PathVariable Long id) {
// BugAssignment bugAssignment =
// bugAssignmentRepository.findById(id).orElse(null);
// if (bugAssignment != null) {
// User tester =
// userRepository.findById(bugAssignmentDTO.getTesterId()).orElse(null);
// User developer =
// userRepository.findById(bugAssignmentDTO.getDeveloperId()).orElse(null);

// if (tester != null && developer != null) {
// bugAssignment.setTester(tester);
// bugAssignment.setDeveloper(developer);
// bugAssignment.setAssignedAt(bugAssignmentDTO.getAssignedAt());
// bugAssignmentRepository.save(bugAssignment);

// BugAssignmentDTO dto = mapToDTO(bugAssignment);
// return ResponseEntity
// .ok(new BugAssignmentApiResponse(200, "Updated Successfully",
// "/api/bug-assignments", dto));
// } else {
// return ResponseEntity.status(400)
// .body(new BugAssignmentApiResponse(400, "Invalid Tester or Developer",
// "/api/bug-assignments"));
// }
// }
// return ResponseEntity.status(404)
// .body(new BugAssignmentApiResponse(404, "Bug Assignment Not Found",
// "/api/bug-assignments"));
// }

// private BugAssignmentDTO mapToDTO(BugAssignment bugAssignment) {
// return new BugAssignmentDTO(
// bugAssignment.getId(),
// bugAssignment.getTester() != null ? bugAssignment.getTester().getName() :
// null,
// bugAssignment.getDeveloper() != null ? bugAssignment.getDeveloper().getName()
// : null,
// bugAssignment.getBug() != null ? bugAssignment.getBug().getId() : null,
// bugAssignment.getAssignedAt());
// }
// }




