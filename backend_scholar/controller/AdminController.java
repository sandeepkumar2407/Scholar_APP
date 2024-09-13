package com.example.demo.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.model.AdminModel;
import com.example.demo.model.StudentModel;
import com.example.demo.model.TeacherModel;
import com.example.demo.repository.AdminRepository;
import com.example.demo.service.StudentService;
import com.example.demo.service.TeacherService;

@RestController
@RequestMapping("/admin")
@CrossOrigin
public class AdminController {

    @Autowired
    private AdminRepository adminRepository;

    @Autowired
    private StudentService studentService;

    @Autowired
    private TeacherService teacherService;

    @PostMapping("/login")
    public ResponseEntity<Map<String, String>> login(@RequestBody AdminModel admin) {
        int id = admin.getId();
        String password = admin.getPassword();

        // Check if admin exists with the given ID and password
        AdminModel existingAdmin = adminRepository.findByIdAndPassword(id, password);
        if (existingAdmin != null) {
            // If admin exists, return success message with name
            Map<String, String> response = new HashMap<>();
            response.put("message", "success");
            response.put("name", existingAdmin.getName()); // Add admin's name
            return ResponseEntity.status(HttpStatus.OK).body(response);
        } else {
            // If admin does not exist, return failure message
            Map<String, String> response = new HashMap<>();
            response.put("message", "failure");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
        }
    }

    @PostMapping("/createStudent")
    public ResponseEntity<String> createStudent(@RequestBody StudentModel student) {
        StudentModel createdStudent = studentService.createStudent(student);
        // return new ResponseEntity<>(createdStudent, HttpStatus.CREATED);
        if (createdStudent != null) {
            return new ResponseEntity<>("Student created successfully",
                    HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>("Failed to create teacher",
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/viewStudents")
    public ResponseEntity<List<StudentModel>> viewStudents() {
        List<StudentModel> students = studentService.getAllStudents();
        return new ResponseEntity<>(students, HttpStatus.OK);
    }

    @DeleteMapping("/deleteStudent/{studentId}")
    public ResponseEntity<String> deleteStudent(@PathVariable("studentId") int studentId) {
        studentService.deleteStudent(studentId);
        return new ResponseEntity<>("Student with ID " + studentId + " deleted successfully", HttpStatus.OK);
    }

    @PostMapping("/createTeacher")
    public ResponseEntity<String> createTeacher(@RequestBody TeacherModel teacher) {
        TeacherModel createdTeacher = teacherService.createTeacher(teacher);
        if (createdTeacher != null) {
            return new ResponseEntity<>("Teacher created successfully",
                    HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>("Failed to create teacher",
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/viewTeachers")
    public ResponseEntity<List<TeacherModel>> viewAllTeachers() {
        List<TeacherModel> teachers = teacherService.getAllTeachers();
        return ResponseEntity.ok(teachers);
    }

    @DeleteMapping("/deleteTeacher/{teacherId}")
    public ResponseEntity<String> deleteTeacher(@PathVariable("teacherId") int teacherId) {
        teacherService.deleteTeacher(teacherId);
        return new ResponseEntity<>("Teacher with ID " + teacherId + " deleted successfully", HttpStatus.OK);
    }

}
