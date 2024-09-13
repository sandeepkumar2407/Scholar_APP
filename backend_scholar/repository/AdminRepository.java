package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.model.AdminModel;

public interface AdminRepository extends JpaRepository<AdminModel, Integer> {

    AdminModel findByIdAndPassword(int id, String password);
}
