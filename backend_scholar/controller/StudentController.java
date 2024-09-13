package com.example.demo.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.model.Assignment;
import com.example.demo.model.ResultList;
import com.example.demo.model.StudentModel;
import com.example.demo.model.Submission;
import com.example.demo.repository.StudentRepository;
import com.example.demo.service.AssignmentService;
import com.example.demo.service.ResultListService;
import com.example.demo.service.SubmissionService;

@RestController
@RequestMapping("/student")
@CrossOrigin
public class StudentController {
    @Autowired
    private AssignmentService assignmentService;

    @Autowired
    private SubmissionService submissionService;

    @Autowired
    private ResultListService resultListService;

    @Autowired
    private StudentRepository studentRepository;

    // Student login
    @PostMapping("/login")
    public ResponseEntity<Map<String, Object>> login(@RequestBody StudentModel student) {
        int id = student.getStudentID();
        String password = student.getStudentPassword();

        // Check if student exists with the given ID and password
        StudentModel existingStudent = studentRepository.findByStudentIDAndStudentPassword(id, password);
        if (existingStudent != null) {
            // If student exists, return the student's ID, name, and section
            String name = existingStudent.getStudentName();
            char section = existingStudent.getStudentSection(); // Get the student's section
            Map<String, Object> response = new HashMap<>();
            response.put("id", id);
            response.put("name", name);
            response.put("section", section); // Include section in the response
            return ResponseEntity.status(HttpStatus.OK).body(response);
        } else {
            // If student does not exist or credentials are incorrect, return an error
            // message
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
        }
    }

    // Assignment...
    // Read
    @GetMapping("/viewAssignments")
    public ResponseEntity<List<Assignment>> viewAllAssignments() {
        List<Assignment> assignments = assignmentService.viewAllAssignments();
        return ResponseEntity.ok(assignments);
    }

    // Submission...
    // Create
    @PostMapping("/submissions")
    public ResponseEntity<Submission> createSubmission(@ModelAttribute SubmissionDTO submissionDTO) {
        Submission createdSubmission = submissionService.createSubmission(
                submissionDTO.getAssignmentId(),
                submissionDTO.getStudentId(),
                submissionDTO.getSubmissionFileLink());

        System.out.println("In studentcontroller createSubmission method");
        // Return the created assignment in the response body with HTTP status 200 OK
        return ResponseEntity.ok().body(createdSubmission);
    }

    // Results...
    // Read
    @GetMapping("/{studentId}/results")
    public Map<Integer, Integer> getResultsForStudent(@PathVariable("studentId") int studentId) {
        Map<Integer, Integer> studentResults = new HashMap<>();
        Iterable<ResultList> allResults = resultListService.getAllResults();

        for (ResultList resultList : allResults) {
            // Check if the result entry corresponds to the given student ID
            if (resultList.getScoreList().containsKey(studentId)) {
                // Get the score for the student from the result entry
                int score = resultList.getScoreList().get(studentId);
                // Add the assignment ID and score to the map
                studentResults.put(resultList.getAssignment().getAssignmentID(), score);
            }
        }
        return studentResults;
    }

}

class SubmissionDTO {
    private int assignmentId;
    private int studentId;
    private String submissionFileLink;

    // Constructors, getters, and setters

    public SubmissionDTO() {
    }

    public SubmissionDTO(int assignmentId, int studentId, String submissionFileLink) {
        this.assignmentId = assignmentId;
        this.studentId = studentId;
        this.submissionFileLink = submissionFileLink;
    }

    public int getAssignmentId() {
        return assignmentId;
    }

    public void setAssignmentId(int assignmentId) {
        this.assignmentId = assignmentId;
    }

    public int getStudentId() {
        return studentId;
    }

    public void setStudentId(int studentId) {
        this.studentId = studentId;
    }

    public String getSubmissionFileLink() {
        return submissionFileLink;
    }

    public void setSubmissionFileLink(String submissionFileLink) {
        this.submissionFileLink = submissionFileLink;
    }
}
