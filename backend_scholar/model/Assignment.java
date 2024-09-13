package com.example.demo.model;

import java.time.LocalDate;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

import jakarta.persistence.*;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "assignment_type")
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, property = "type")
@JsonSubTypes({
        @JsonSubTypes.Type(value = AssignmentWithOnlyDescription.class, name = "AssignmentWithOnlyDescription"),
        @JsonSubTypes.Type(value = AssignmentWithOnlyFile.class, name = "AssignmentWithOnlyFile"),
        @JsonSubTypes.Type(value = AssignmentWithFileAndDescription.class, name = "AssignmentWithFileAndDescription")
})
public abstract class Assignment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "assignment_id")
    protected int assignmentID;

    protected String description;
    protected String fileLink;
    protected String submissionLink;
    protected LocalDate deadline;
    protected char section;

    @Transient
    protected String assignmentType;

    public int getAssignmentID() {
        return assignmentID;
    }

    public abstract String getDescription();

    public abstract void setDescription(String description);

    public abstract String getFileLink();

    public abstract void setFileLink(String fileLink);

    public String getSubmissionLink() {
        return submissionLink;
    }

    public void setSubmissionLink(String submissionLink) {
        this.submissionLink = submissionLink;
    }

    public LocalDate getDeadline() {
        return deadline;
    }

    public void setDeadline(LocalDate deadline) {
        this.deadline = deadline;
    }

    public char getSection() {
        return section;
    }

    public void setSection(char section) {
        this.section = section;
    }

}

@Entity
class AssignmentWithOnlyDescription extends Assignment {
    public AssignmentWithOnlyDescription(String description, String submissionLink, LocalDate deadline, char section) {
        this.description = description;
        this.submissionLink = submissionLink;
        this.deadline = deadline;
        this.section = section;
    }

    public AssignmentWithOnlyDescription() {
    }

    @Override
    public String getDescription() {
        return description;
    }

    @Override
    public String getFileLink() {
        return null;
    }

    @Override
    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public void setFileLink(String fileLink) {
        this.fileLink = null;
    }
}

@Entity
class AssignmentWithOnlyFile extends Assignment {
    public AssignmentWithOnlyFile(String fileLink, String submissionLink, LocalDate deadline, char section) {
        this.fileLink = fileLink;
        this.submissionLink = submissionLink;
        this.deadline = deadline;
        this.section = section;
    }

    public AssignmentWithOnlyFile() {
    }

    @Override
    public String getDescription() {
        return null;
    }

    @Override
    public String getFileLink() {
        return fileLink;
    }

    @Override
    public void setDescription(String description) {
        this.description = null;
    }

    @Override
    public void setFileLink(String fileLink) {
        this.fileLink = fileLink;
    }

}

@Entity
class AssignmentWithFileAndDescription extends Assignment {
    public AssignmentWithFileAndDescription(String description, String fileLink,
            String submissionLink, LocalDate deadline, char section) {
        this.description = description;
        this.fileLink = fileLink;
        this.submissionLink = submissionLink;
        this.deadline = deadline;
        this.section = section;
    }

    public AssignmentWithFileAndDescription() {
    }

    @Override
    public String getDescription() {
        return description;
    }

    @Override
    public String getFileLink() {
        return fileLink;
    }

    @Override
    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public void setFileLink(String fileLink) {
        this.fileLink = fileLink;
    }

}
