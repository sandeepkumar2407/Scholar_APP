package com.example.demo.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.demo.model.Assignment;
import com.example.demo.model.ResultList;

@Repository
public interface ResultRepository extends JpaRepository<ResultList, Integer> {

    void deleteByAssignment(Assignment assignment);

    Optional<ResultList> findByAssignment(Assignment assignment);

    @Query("SELECT r FROM ResultList r JOIN r.scoreList s WHERE KEY(s) = :student_id")
    List<ResultList> findAllByScoreListContainingKey(int studentId);

    @Query("SELECT r FROM ResultList r JOIN r.assignment a WHERE a.section = :section")
    List<ResultList> findAllBySection(@Param("section") char section);

}
