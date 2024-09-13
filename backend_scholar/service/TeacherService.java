package com.example.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.model.TeacherModel;
import com.example.demo.repository.TeacherRepository;

import jakarta.persistence.EntityNotFoundException;

@Service
public class TeacherService {
    @Autowired
    private TeacherRepository teacherRepository;

    public TeacherModel createTeacher(TeacherModel teacher) {
        // Save the teacher entity to the database
        return teacherRepository.save(teacher);
    }

    public TeacherModel getTeacherById(int teacherId) {
        return teacherRepository.findById(teacherId).orElse(null);
    }

    public List<TeacherModel> getAllTeachers() {
        return teacherRepository.findAll();
    }

    public void deleteTeacher(Integer teacherId) {
        // Find the teacher by their ID
        TeacherModel teacher = teacherRepository.findById(teacherId)
                .orElseThrow(() -> new EntityNotFoundException("Teacher not found with id: " + teacherId));

        // Delete the teacher
        teacherRepository.delete(teacher);
    }

}
