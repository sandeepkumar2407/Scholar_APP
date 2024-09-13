package com.example.demo.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.model.Assignment;
import com.example.demo.model.ResultList;
import com.example.demo.model.StudentModel;
import com.example.demo.repository.AssignmentRepository;
import com.example.demo.repository.ResultRepository;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;

@Service
public class ResultListService {

    @Autowired
    private ResultRepository resultRepository;

    @Autowired
    private AssignmentRepository assignmentRepository;

    // May not be used.
    public Map<Integer, Integer> createScoreMap(StudentModel[] students, Integer[] scores) {
        // Initialize a map to store student IDs and their corresponding scores
        Map<Integer, Integer> scoreMap = new HashMap<>();

        // Iterate over the arrays of students and scores
        for (int i = 0; i < students.length; i++) {
            // Get the student ID and score from the input arrays
            int studentId = students[i].getStudentID();
            int score = scores[i];

            // Add the student ID and score to the map
            scoreMap.put(studentId, score);
        }

        // Return the populated score map
        return scoreMap;
    }

    // May not be used.
    @Transactional
    public boolean createOrUpdateResultListForAssignment(Assignment assignment, StudentModel[] students,
            Integer[] scores) {
        // Create the score map using the provided method
        Map<Integer, Integer> scoreMap = createScoreMap(students, scores);

        // Create a new ResultList entity for the assignment
        ResultList resultList = new ResultList(assignment, scoreMap);

        resultList.setEvaluated();
        // Save the new ResultList
        resultRepository.save(resultList);

        return true;
    }

    // Create
    // public ResultList createResultListForAssignment(int assignmentId,
    // Map<Integer, Integer> scoreList) {
    // // Find the assignment by ID
    // Assignment assignment =
    // assignmentRepository.findById(assignmentId).orElse(null);

    // if (assignment == null || scoreList == null) {
    // return null;
    // }

    // // Create a new ResultList entity for the assignment
    // ResultList resultList = new ResultList(assignment, scoreList);

    // // Save the new ResultList
    // resultRepository.save(resultList);

    // return resultList;
    // }
    public ResultList createOrUpdateResultListForAssignment(int assignmentId, int studentId, int score) {
        // Find the assignment by ID
        Assignment assignment = assignmentRepository.findById(assignmentId).orElse(null);

        if (assignment == null) {
            return null;
        }

        // Find existing ResultList or create a new one
        ResultList resultList = resultRepository.findByAssignment(assignment).orElse(new ResultList(assignment));

        // Add or update score for the student
        resultList.createOrUpdateScores(studentId, score);

        // Save the ResultList
        resultRepository.save(resultList);

        return resultList;
    }

    // Read
    public ResultList getResultListForAssignment(Assignment assignment) {
        // Retrieve the result list for the given assignment
        Optional<ResultList> resultListOptional = resultRepository.findByAssignment(assignment);

        // Check if the result list exists
        if (resultListOptional.isPresent()) {
            return resultListOptional.get();
        } else {
            throw new EntityNotFoundException("Result list not found for assignment: " + assignment);
            // return null;
        }
    }

    // Read
    public List<ResultList> getResultsForStudent(int studentId) {
        return resultRepository.findAllByScoreListContainingKey(studentId);
    }

    public List<ResultList> getAllResults() {
        return resultRepository.findAll();
    }

}
