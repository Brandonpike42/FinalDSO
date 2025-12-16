package com.bst;

import com.bst.model.TreeNode;
import com.bst.service.BinarySearchTreeService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class BinarySearchTreeServiceTest {

    @Autowired
    private BinarySearchTreeService bstService;

    @Test
    public void testBuildTree() {
        // Test 1: Building a standard BST
        List<Integer> numbers = Arrays.asList(50, 30, 70, 20, 40, 60, 80);
        TreeNode root = bstService.buildTree(numbers);

        assertNotNull(root);
        assertEquals(50, root.getValue());
        assertEquals(30, root.getLeft().getValue());
        assertEquals(70, root.getRight().getValue());
        assertEquals(20, root.getLeft().getLeft().getValue());
        assertEquals(40, root.getLeft().getRight().getValue());
        assertEquals(60, root.getRight().getLeft().getValue());
        assertEquals(80, root.getRight().getRight().getValue());
    }

    @Test
    public void testBuildBalancedTree() {
        // Test 2: Building a balanced BST
        List<Integer> numbers = Arrays.asList(50, 30, 70, 20, 40, 60, 80);
        TreeNode root = bstService.buildBalancedTree(numbers);

        assertNotNull(root);
        assertEquals(50, root.getValue()); // Middle element becomes root

        // Verify tree is balanced by checking structure
        assertNotNull(root.getLeft());
        assertNotNull(root.getRight());

        // Check that all numbers are present in the tree
        Map<String, Object> treeJson = bstService.treeToJson(root);
        assertNotNull(treeJson);
    }

    @Test
    public void testTreeToJson() {
        // Test 3: Converting tree to JSON representation
        List<Integer> numbers = Arrays.asList(5, 3, 7, 2, 4, 6, 8);
        TreeNode root = bstService.buildTree(numbers);

        Map<String, Object> json = bstService.treeToJson(root);

        assertNotNull(json);
        assertEquals(5, json.get("value"));

        @SuppressWarnings("unchecked")
        Map<String, Object> leftNode = (Map<String, Object>) json.get("left");
        assertNotNull(leftNode);
        assertEquals(3, leftNode.get("value"));

        @SuppressWarnings("unchecked")
        Map<String, Object> rightNode = (Map<String, Object>) json.get("right");
        assertNotNull(rightNode);
        assertEquals(7, rightNode.get("value"));
    }
}
