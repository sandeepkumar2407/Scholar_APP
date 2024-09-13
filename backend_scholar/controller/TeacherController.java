package com.example.demo.controller;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.model.Assignment;
import com.example.demo.model.ResultList;
import com.example.demo.model.Submission;
import com.example.demo.model.TeacherModel;
import com.example.demo.repository.TeacherRepository;
import com.example.demo.service.AssignmentService;
import com.example.demo.service.ResultListService;
import com.example.demo.service.SubmissionService;

// import jakarta.persistence.EntityNotFoundException;

@RestController
@RequestMapping("/teacher")
@CrossOrigin
public class TeacherController {
    @Autowired
    private AssignmentService assignmentService;

    @Autowired
    private SubmissionService submissionService;

    @Autowired
    private ResultListService resultListService;

    @Autowired
    private TeacherRepository teacherRepository;

    public TeacherController() {
    }

    // Teacher login
    @PostMapping("/login")
    public ResponseEntity<Map<String, Object>> login(@RequestBody TeacherModel teacher) {
        int id = teacher.getTeacherID();
        String password = teacher.getTeacherPassword();

        // Check if teacher exists with the given ID and password
        TeacherModel existingTeacher = teacherRepository.findByTeacherIDAndTeacherPassword(id, password);
        if (existingTeacher != null) {
            // If teacher exists, return the teacher's ID and name
            String name = existingTeacher.getTeacherName();
            Map<String, Object> response = new HashMap<>();
            response.put("id", id);
            response.put("name", name);
            return ResponseEntity.status(HttpStatus.OK).body(response);
        } else {
            // If teacher does not exist or credentials are incorrect, return an error
            // message
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
        }
    }

    // Assignments......
    // Create
    @PostMapping("/createAssignment")
    public ResponseEntity<Assignment> createAssignment(@ModelAttribute AssignmentDTO assignmentDTO) {
        // Create the assignment using the provided parameters
        Assignment createdAssignment = assignmentService.createAssignment(
                assignmentDTO.getDescription(),
                assignmentDTO.getFileLink(),
                assignmentDTO.getSubmissionLink(),
                assignmentDTO.getSection(),
                assignmentDTO.getDeadline());

        System.out.println("In teachercontroller createAssignment method");
        // Return the created assignment in the response body with HTTP status 200 OK
        return ResponseEntity.ok().body(createdAssignment);
    }

    // Read
    @GetMapping("/viewAssignments")
    public ResponseEntity<List<Assignment>> viewAllAssignments() {
        List<Assignment> assignments = assignmentService.viewAllAssignments();
        return ResponseEntity.ok(assignments);
    }

    // Delete
    @DeleteMapping("/assignments/{assignmentId}")
    public void deleteAssignment(@PathVariable int assignmentId) {
        Assignment assignment = assignmentService.getAssignmentById(assignmentId);
        if (assignment != null) {
            assignmentService.deleteAssignmentWithSubmissionsAndResults(assignment);
        }
    }

    // For Submissions...
    // Read
    @GetMapping("/assignments/{assignmentId}/submissions")
    public List<Submission> viewSubmissionsForAssignment(@PathVariable("assignmentId") int assignmentId) {
        return submissionService.getSubmissionsForAssignment(assignmentId);
    }

    // Delete
    @DeleteMapping("/submissions/{assignmentId}")
    public void deleteSubmissionsForAssignment(@PathVariable int assignmentId) {
        submissionService.deleteSubmissionsForAssignment(assignmentId);
    }

    // For Results...
    // Create and Update
    @PostMapping("/resultlists")
    public ResponseEntity<ResultList> createOrUpdateResultList(@RequestBody ResultListDTO resultListDTO) {
        ResultList resultList = resultListService.createOrUpdateResultListForAssignment(
                resultListDTO.getAssignmentID(),
                resultListDTO.getStudentID(),
                resultListDTO.getScore());
        if (resultList != null) {
            return ResponseEntity.ok(resultList);
        } else {
            return ResponseEntity.badRequest().body(null);
        }
    }

    // Read
    @GetMapping("/resultlist")
    public ResponseEntity<List<ResultList>> getAllResultLists() {
        List<ResultList> resultLists = resultListService.getAllResults();

        if (resultLists.isEmpty()) {
            return ResponseEntity.notFound().build();
        } else {
            return ResponseEntity.ok(resultLists);
        }
    }

}

class ResultListDTO {

    private int assignmentID;
    private int studentID;
    private int score;

    public ResultListDTO(int assignmentID, int studentID, int score) {
        this.assignmentID = assignmentID;
        this.studentID = studentID;
        this.score = score;
    }

    public ResultListDTO() {
    }

    public int getAssignmentID() {
        return assignmentID;
    }

    public void setAssignmentID(int assignmentID) {
        this.assignmentID = assignmentID;
    }

    public int getStudentID() {
        return studentID;
    }

    public void setStudentID(int studentID) {
        this.studentID = studentID;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

}

class AssignmentDTO {
    private String description;
    private String fileLink;
    private char section;
    private String submissionLink;
    private LocalDate deadline;

    public AssignmentDTO(String description, String submissionLink, String fileLink, char section, LocalDate deadline) {
        this.description = description;
        this.fileLink = fileLink;
        this.section = section;
        this.deadline = deadline;
        this.submissionLink = submissionLink;
    }

    public AssignmentDTO() {

    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getFileLink() {
        return fileLink;
    }

    public void setFileLink(String fileLink) {
        this.fileLink = fileLink;
    }

    public char getSection() {
        return section;
    }

    public void setSection(char section) {
        this.section = section;
    }

    public LocalDate getDeadline() {
        return deadline;
    }

    public void setDeadline(LocalDate deadline) {
        this.deadline = deadline;
    }

    public String getSubmissionLink() {
        return submissionLink;
    }

    public void setSubmissionLink(String submissionLink) {
        this.submissionLink = submissionLink;
    }

}
