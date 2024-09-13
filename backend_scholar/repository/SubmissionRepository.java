package com.example.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.demo.model.Submission;

@Repository
public interface SubmissionRepository extends JpaRepository<Submission, Integer> {

    // void deleteByAssignment(int assignmentId);

    void deleteByAssignmentAssignmentID(int assignmentID);

    boolean existsByAssignmentAssignmentIDAndStudentStudentID(int assignmentID, int studentID);

    // List<Submission> findByAssignmentId(int assignmentId);

    List<Submission> findByAssignmentAssignmentID(int assignmentID);

    // @Query("SELECT s FROM Submission s JOIN s.student student WHERE
    // student.studentID = :studentID")
    // List<Submission> findAllByStudentID(Integer studentID);
    @Query("SELECT s FROM Submission s JOIN s.student student WHERE student.studentID = :studentID")
    List<Submission> findAllByStudentID(@Param("studentID") Integer studentID);
}
