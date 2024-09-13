package com.example.demo.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.model.ResultList;
// import com.example.demo.model.Assignment;
import com.example.demo.model.StudentModel;
import com.example.demo.model.Submission;
import com.example.demo.repository.ResultRepository;
// import com.example.demo.repository.AssignmentRepository;
import com.example.demo.repository.StudentRepository;
import com.example.demo.repository.SubmissionRepository;

import jakarta.persistence.EntityNotFoundException;

@Service
public class StudentService {
    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private SubmissionRepository submissionRepository;

    @Autowired
    private ResultRepository resultRepository;

    public StudentModel getStudentById(int studentId) {
        return studentRepository.findById(studentId).orElse(null);
    }

    public StudentModel createStudent(StudentModel student) {
        // Save the student entity to the database
        return studentRepository.save(student);
    }

    public List<StudentModel> getAllStudents() {
        // Retrieve all students from the database
        return studentRepository.findAll();
    }

    public void deleteStudent(Integer studentId) {
        // Find the student by their ID
        StudentModel student = studentRepository.findById(studentId)
                .orElseThrow(() -> new EntityNotFoundException("Student not found with id: " + studentId));

        // Delete all submissions associated with the student
        // List<Submission> submissions =
        // submissionRepository.findAllByStudentID(studentId);
        List<Submission> submissions = submissionRepository.findAllByStudentID(studentId);

        submissionRepository.deleteAll(submissions);

        // Fetch all result lists for assignments in the same section as the student
        List<ResultList> resultList = resultRepository.findAllBySection(student.getStudentSection());

        // Iterate over each result list
        for (ResultList resultlist : resultList) {
            // Fetch the score list for the current result list
            Map<Integer, Integer> scoreList = resultlist.getScoreList();

            // Check if the student's ID exists in the score list
            if (scoreList.containsKey(studentId)) {
                // Remove the student's ID from the score list
                scoreList.remove(studentId);

                // Update the result list with the modified score list
                resultlist.setScoreList(scoreList);

                // Save the updated result list
                resultRepository.save(resultlist);
            }
        }

        // Finally, delete the student
        studentRepository.delete(student);
    }

}