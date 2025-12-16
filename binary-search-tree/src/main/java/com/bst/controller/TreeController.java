package com.bst.controller;

import com.bst.model.TreeNode;
import com.bst.model.TreeRecord;
import com.bst.service.BinarySearchTreeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.util.*;
import java.util.stream.Collectors;

@Controller
public class TreeController {

    @Autowired
    private BinarySearchTreeService bstService;

    @GetMapping("/enter-numbers")
    public String enterNumbers() {
        return "enter-numbers";
    }

    @PostMapping("/process-numbers")
    @ResponseBody
    public ResponseEntity<Map<String, Object>> processNumbers(
            @RequestParam String numbers,
            @RequestParam(defaultValue = "false") boolean balanced) {

        try {
            List<Integer> numberList = Arrays.stream(numbers.split(","))
                    .map(String::trim)
                    .filter(s -> !s.isEmpty())
                    .map(Integer::parseInt)
                    .collect(Collectors.toList());

            if (numberList.isEmpty()) {
                return ResponseEntity.badRequest()
                        .body(Map.of("error", "Please enter at least one number"));
            }

            TreeNode tree = balanced ?
                    bstService.buildBalancedTree(numberList) :
                    bstService.buildTree(numberList);

            Map<String, Object> treeJson = bstService.treeToJson(tree);

            bstService.saveTreeRecord(numberList, tree, balanced);

            Map<String, Object> response = new HashMap<>();
            response.put("tree", treeJson);
            response.put("inputNumbers", numberList);
            response.put("balanced", balanced);

            return ResponseEntity.ok(response);

        } catch (NumberFormatException e) {
            return ResponseEntity.badRequest()
                    .body(Map.of("error", "Invalid number format. Please enter comma-separated integers."));
        } catch (Exception e) {
            return ResponseEntity.internalServerError()
                    .body(Map.of("error", "An error occurred: " + e.getMessage()));
        }
    }

    @GetMapping("/previous-trees")
    public String previousTrees(Model model) {
        List<TreeRecord> records = bstService.getAllTreeRecords();
        model.addAttribute("records", records);
        return "previous-trees";
    }
}
