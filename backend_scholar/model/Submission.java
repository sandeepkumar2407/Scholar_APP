package com.example.demo.model;

import java.time.LocalDate;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class Submission {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int submissionID;

    @ManyToOne
    @JoinColumn(name = "assignment_id", nullable = false)
    private Assignment assignment;

    @ManyToOne
    @JoinColumn(name = "student_id", nullable = false)
    private StudentModel student;

    private String submissionFileLink;
    private LocalDate dateOfSubmission;
    private String submissionStatus;

    public Submission(Assignment assignment, StudentModel student, String submissionFileLink) {
        this.assignment = assignment;
        this.student = student;
        this.submissionFileLink = submissionFileLink;
        this.dateOfSubmission = LocalDate.now();
    }

    public Submission() {
    }

    public int getSubmissionID() {
        return submissionID;
    }

    public void setSubmissionID(int submissionID) {
        this.submissionID = submissionID;
    }

    public Assignment getAssignment() {
        return assignment;
    }

    public void setAssignment(Assignment assignment) {
        this.assignment = assignment;
    }

    public StudentModel getStudent() {
        return student;
    }

    public void setStudent(StudentModel student) {
        this.student = student;
    }

    public String getSubmissionFileLink() {
        return submissionFileLink;
    }

    public void setSubmissionFileLink(String submissionFileLink) {
        this.submissionFileLink = submissionFileLink;
    }

    public LocalDate getDateOfSubmission() {
        return dateOfSubmission;
    }

    public String getSubmissionStatus() {
        return submissionStatus;
    }

    public void setSubmissionStatus(String submissionStatus) {
        this.submissionStatus = submissionStatus;
    }

}
