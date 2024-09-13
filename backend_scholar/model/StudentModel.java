package com.example.demo.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "studentmodel")
public class StudentModel {

    @Id // Specify the primary key field
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "student_id")
    private int studentID;

    private String studentName;

    private char studentSection;

    private String studentPassword;
    private final String role = "student";

    public StudentModel() {
    }

    public StudentModel(String studentName, char studentSection) {
        this.studentName = studentName;
        this.studentSection = studentSection;
    }

    public int getStudentID() {
        return studentID;
    }

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public char getStudentSection() {
        return studentSection;
    }

    public void setStudentSection(char studentSection) {
        this.studentSection = studentSection;
    }

    public String getStudentPassword() {
        return studentPassword;
    }

    public void setStudentPassword(String studentPassword) {
        this.studentPassword = studentPassword;
    }

    public String getRole() {
        return role;
    }

}
