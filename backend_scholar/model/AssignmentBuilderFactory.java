package com.example.demo.model;

import java.time.LocalDate;

import org.springframework.stereotype.Component;

@Component
public class AssignmentBuilderFactory {

    public static Assignment createAssignmentWithOnlyDescription(String description, String submissionLink,
            LocalDate deadline, char section) {
        return new AssignmentWithOnlyDescription(description, submissionLink, deadline, section);
    }

    public static Assignment createAssignmentWithOnlyFile(String fileLink, String submissionLink, LocalDate deadline,
            char section) {
        return new AssignmentWithOnlyFile(fileLink, submissionLink, deadline, section);
    }

    public static Assignment createAssignmentWithFileAndDescription(String description, String fileLink,
            String submissionLink, LocalDate deadline, char section) {
        return new AssignmentWithFileAndDescription(description, fileLink, submissionLink, deadline, section);
    }
}
