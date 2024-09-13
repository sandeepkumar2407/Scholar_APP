package com.example.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.model.Assignment;
import com.example.demo.model.StudentModel;
import com.example.demo.model.Submission;
import com.example.demo.repository.AssignmentRepository;
import com.example.demo.repository.StudentRepository;
import com.example.demo.repository.SubmissionRepository;
import com.example.demo.model.LateSubmissionChecker;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;

@Service
public class SubmissionService {

    private final SubmissionRepository submissionRepository;
    private final AssignmentRepository assignmentRepository;
    private final StudentRepository studentRepository;

    @Autowired
    public SubmissionService(SubmissionRepository submissionRepository,
            AssignmentRepository assignmentRepository,
            StudentRepository studentRepository) {
        this.submissionRepository = submissionRepository;
        this.assignmentRepository = assignmentRepository;
        this.studentRepository = studentRepository;
    }

    // Create
    @Transactional
    public Submission createSubmission(int assignmentId, int studentId, String submissionFileLink) {
        // Check if a submission already exists for the given assignment and student
        boolean submissionExists = submissionRepository.existsByAssignmentAssignmentIDAndStudentStudentID(assignmentId,
                studentId);
        if (submissionExists) {
            return null;
        }

        // Retrieve the assignment
        Assignment assignment = assignmentRepository.findById(assignmentId)
                .orElseThrow(() -> new EntityNotFoundException("Assignment not found with ID: " + assignmentId));

        // Retrieve the student
        StudentModel student = studentRepository.findById(studentId)
                .orElseThrow(() -> new EntityNotFoundException("Student not found with ID: " + studentId));

        // Create a new submission
        Submission submission = new Submission(assignment, student, submissionFileLink);

        // Get the singleton instance of LateSubmissionChecker
        LateSubmissionChecker lateSubmissionChecker = LateSubmissionChecker.getInstance();

        String submissionStatus = lateSubmissionChecker.checkLateSubmissions(assignment, submission);

        submission.setSubmissionStatus(submissionStatus);

        submissionRepository.save(submission);
        return submission;
    }

    // Read
    public List<Submission> findSubmissionsByAssignmentId(Assignment assignment) {
        return submissionRepository.findByAssignmentAssignmentID(assignment.getAssignmentID());
    }

    // Read
    public List<Submission> getSubmissionsForAssignment(int assignmentId) {
        return submissionRepository.findByAssignmentAssignmentID(assignmentId);
    }

    // Delete
    public void deleteSubmissionsForAssignment(int assignmentId) {
        submissionRepository.deleteByAssignmentAssignmentID(assignmentId);
    }

}
