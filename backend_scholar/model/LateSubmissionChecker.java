package com.example.demo.model;

import org.springframework.stereotype.Component;

@Component
public class LateSubmissionChecker {

    private static LateSubmissionChecker lateSubmissionChecker = new LateSubmissionChecker();

    private LateSubmissionChecker() {
    }

    public static LateSubmissionChecker getInstance() {
        return lateSubmissionChecker;
    }

    public String checkLateSubmissions(Assignment assignment, Submission submission) {
        if (submission.getDateOfSubmission().isAfter(assignment.getDeadline()))
            return "Late";
        return "In-Time";
    }

}
