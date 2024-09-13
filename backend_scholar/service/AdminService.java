package com.example.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.model.AdminModel;
import com.example.demo.model.StudentModel;
import com.example.demo.model.TeacherModel;
import com.example.demo.repository.AdminRepository;
import com.example.demo.repository.StudentRepository;
import com.example.demo.repository.TeacherRepository;

@Service
public class AdminService {

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private TeacherRepository teacherRepository;

    @Autowired
    private AdminRepository adminRepository;

    public StudentModel saveStudent(StudentModel student) {
        return studentRepository.save(student);
    }

    public List<StudentModel> getAllStudents() {
        return studentRepository.findAll();
    }

    public void deleteStudent(int studentId) {
        studentRepository.deleteById(studentId);
    }

    public TeacherModel saveTeacher(TeacherModel teacher) {
        return teacherRepository.save(teacher);
    }

    public List<TeacherModel> getAllTeachers() {
        return teacherRepository.findAll();
    }

    public void deleteTeacher(int teacherId) {
        teacherRepository.deleteById(teacherId);
    }

    public StudentModel getStudentById(int studentId) {
        return studentRepository.findById(studentId)
                .orElse(null);
    }

    public TeacherModel getTeacherById(int teacherId) {
        return teacherRepository.findById(teacherId)
                .orElse(null);
    }

    public AdminModel saveAdmin(AdminModel admin) {
        // Check if admin already exists
        int id = admin.getId();
        String password = admin.getPassword();
        AdminModel existingAdmin = adminRepository.findByIdAndPassword(id, password);
        if (existingAdmin != null) {
            return null; // Admin already exists
        }

        // Save the new admin
        return adminRepository.save(admin);
    }
}
