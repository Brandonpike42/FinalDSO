package com.bst.service;

import com.bst.model.TreeNode;
import com.bst.model.TreeRecord;
import com.bst.repository.TreeRecordRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.*;

@Service
public class BinarySearchTreeService {

    @Autowired
    private TreeRecordRepository treeRecordRepository;

    @Autowired
    private ObjectMapper objectMapper;

    public TreeNode insert(TreeNode root, Integer value) {
        if (root == null) {
            return new TreeNode(value);
        }

        if (value < root.getValue()) {
            root.setLeft(insert(root.getLeft(), value));
        } else if (value > root.getValue()) {
            root.setRight(insert(root.getRight(), value));
        }

        return root;
    }

    public TreeNode buildTree(List<Integer> numbers) {
        TreeNode root = null;
        for (Integer num : numbers) {
            root = insert(root, num);
        }
        return root;
    }

    public TreeNode buildBalancedTree(List<Integer> numbers) {
        List<Integer> sorted = new ArrayList<>(numbers);
        Collections.sort(sorted);
        return buildBalancedTreeHelper(sorted, 0, sorted.size() - 1);
    }

    private TreeNode buildBalancedTreeHelper(List<Integer> sorted, int start, int end) {
        if (start > end) {
            return null;
        }

        int mid = start + (end - start) / 2;
        TreeNode node = new TreeNode(sorted.get(mid));
        node.setLeft(buildBalancedTreeHelper(sorted, start, mid - 1));
        node.setRight(buildBalancedTreeHelper(sorted, mid + 1, end));

        return node;
    }

    public Map<String, Object> treeToJson(TreeNode root) {
        if (root == null) {
            return null;
        }

        Map<String, Object> node = new HashMap<>();
        node.put("value", root.getValue());
        node.put("left", treeToJson(root.getLeft()));
        node.put("right", treeToJson(root.getRight()));

        return node;
    }

    public TreeRecord saveTreeRecord(List<Integer> numbers, TreeNode tree, boolean balanced) {
        try {
            String inputNumbers = numbers.toString();
            String treeStructure = objectMapper.writeValueAsString(treeToJson(tree));

            TreeRecord record = new TreeRecord(inputNumbers, treeStructure, balanced);
            return treeRecordRepository.save(record);
        } catch (Exception e) {
            throw new RuntimeException("Error saving tree record", e);
        }
    }

    public List<TreeRecord> getAllTreeRecords() {
        return treeRecordRepository.findAllByOrderByCreatedAtDesc();
    }
}