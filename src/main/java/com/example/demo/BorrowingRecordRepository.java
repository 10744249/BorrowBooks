package com.example.demo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface BorrowingRecordRepository extends JpaRepository<BorrowingRecord, Long> {
    // 新增方法以便根據用戶ID和庫存ID查找特定的借閱記錄
    Optional<BorrowingRecord> findByUserIdAndInventoryId(Long userId, Long inventoryId);

    // 可以根據需要添加其他自定義查詢方法
}
