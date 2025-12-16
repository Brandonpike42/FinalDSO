package com.bst.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "tree_records")
public class TreeRecord {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 1000)
    private String inputNumbers;

    @Column(nullable = false, length = 5000)
    private String treeStructure;

    @Column(nullable = false)
    private LocalDateTime createdAt;

    @Column(nullable = false)
    private boolean balanced;

    public TreeRecord() {
        this.createdAt = LocalDateTime.now();
        this.balanced = false;
    }

    public TreeRecord(String inputNumbers, String treeStructure, boolean balanced) {
        this.inputNumbers = inputNumbers;
        this.treeStructure = treeStructure;
        this.createdAt = LocalDateTime.now();
        this.balanced = balanced;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getInputNumbers() {
        return inputNumbers;
    }

    public void setInputNumbers(String inputNumbers) {
        this.inputNumbers = inputNumbers;
    }

    public String getTreeStructure() {
        return treeStructure;
    }

    public void setTreeStructure(String treeStructure) {
        this.treeStructure = treeStructure;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public boolean isBalanced() {
        return balanced;
    }

    public void setBalanced(boolean balanced) {
        this.balanced = balanced;
    }
}