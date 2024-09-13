package com.example.demo.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.model.Assignment;
import com.example.demo.model.AssignmentBuilderFactory;
import com.example.demo.repository.AssignmentRepository;
import com.example.demo.repository.ResultRepository;
import com.example.demo.repository.SubmissionRepository;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;

@Service
public class AssignmentService {

    private final AssignmentRepository assignmentRepository;
    private final SubmissionRepository submissionRepository;
    private final ResultRepository resultRepository;

    @Autowired
    public AssignmentService(AssignmentRepository assignmentRepository, SubmissionRepository submissionRepository,
            ResultRepository resultRepository, AssignmentBuilderFactory assignmentBuilderFactory) {
        this.assignmentRepository = assignmentRepository;
        this.submissionRepository = submissionRepository;
        this.resultRepository = resultRepository;
    }

    @Transactional
    public void deleteAssignmentWithSubmissionsAndResults(/* int assignmentId */ Assignment assignment) {

        // Delete results related to the assignment
        resultRepository.deleteByAssignment(assignment);

        // Delete submissions related to the assignment
        submissionRepository.deleteByAssignmentAssignmentID(assignment.getAssignmentID());

        // Finally, delete the assignment itself
        assignmentRepository.delete(assignment);
    }

    public Assignment createAssignment(String description, String fileLink, String submissionLink, char section,
            LocalDate deadline) {
        System.out.println("Entered service...");
        // Validate that at least description or file link is provided
        if (description == null && fileLink == null) {
            throw new IllegalArgumentException("At least description or file link must be provided.");
        }

        // Save the assignment using the appropriate builder method based on its
        // attributes
        if (description != null && fileLink != null) {
            return assignmentRepository.save(AssignmentBuilderFactory.createAssignmentWithFileAndDescription(
                    description, fileLink, submissionLink, deadline, section));
        } else if (description != null) {
            return assignmentRepository.save(AssignmentBuilderFactory.createAssignmentWithOnlyDescription(
                    description, submissionLink, deadline, section));
        } else {
            return assignmentRepository.save(AssignmentBuilderFactory.createAssignmentWithOnlyFile(
                    fileLink, submissionLink, deadline, section));
        }
    }

    public List<Assignment> viewAllAssignments() {
        return assignmentRepository.findAll();
    }

    public Assignment getAssignmentById(int assignmentId) {
        return assignmentRepository.findById(assignmentId)
                .orElseThrow(() -> new EntityNotFoundException("Assignment not found with ID: " + assignmentId));
    }

    public List<Assignment> getAssignmentsBySection(char section) {
        return assignmentRepository.findBySection(section);
    }

}
