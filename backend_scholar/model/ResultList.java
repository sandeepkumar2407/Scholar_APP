package com.example.demo.model;

import java.util.HashMap;
import java.util.Map;

import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.MapKeyColumn;
import jakarta.persistence.OneToOne;

@Entity
public class ResultList {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int resultID;

    @OneToOne
    @JoinColumn(name = "assignment_id", unique = true)
    private Assignment assignment;

    @ElementCollection
    @CollectionTable(name = "result_list_scores", joinColumns = @JoinColumn(name = "result_id"))
    @MapKeyColumn(name = "student_id")
    @Column(name = "score")
    private Map<Integer, Integer> scoreList;

    private boolean evaluated = false;

    public ResultList() {
        scoreList = new HashMap<Integer, Integer>();
    }

    public ResultList(Assignment assignment, Map<Integer, Integer> scoreList) {
        this.assignment = assignment;
        this.scoreList = scoreList;
    }

    public ResultList(Assignment assignment) {
        this.assignment = assignment;
        scoreList = new HashMap<Integer, Integer>();
    }

    public Assignment getAssignment() {
        return assignment;
    }

    public void setAssignment(Assignment assignment) {
        this.assignment = assignment;
    }

    public Map<Integer, Integer> getScoreList() {
        return scoreList;
    }

    public void setScoreList(Map<Integer, Integer> scoreList) {
        this.scoreList = scoreList;
    }

    public void createOrUpdateScores(int studentID, int score) {
        this.scoreList.put(studentID, score);
    }

    public boolean getEvaluated() {
        return evaluated;
    }

    public void setEvaluated() {
        this.evaluated = true;
    }
}