package com.bst.repository;

import com.bst.model.TreeRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface TreeRecordRepository extends JpaRepository<TreeRecord, Long> {
    List<TreeRecord> findAllByOrderByCreatedAtDesc();
}