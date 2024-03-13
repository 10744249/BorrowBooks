package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Date;
import java.util.Optional;

@Service
public class BookService {

    private final BookRepository bookRepository;
    private final BorrowingRecordRepository borrowingRecordRepository;

    @Autowired
    public BookService(BookRepository bookRepository, BorrowingRecordRepository borrowingRecordRepository) {
        this.bookRepository = bookRepository;
        this.borrowingRecordRepository = borrowingRecordRepository;
    }

    public boolean borrowBook(Long userId, Long inventoryId) {
        // 檢查書籍是否可借
        Optional<Inventory> inventory = bookRepository.findById(inventoryId);
        if (!inventory.isPresent() || !inventory.get().getStatus().equals("可借閱")) {
            return false; // 書籍不存在或已借出
        }

        // 更新書籍狀態為"借出"
        Inventory bookToBorrow = inventory.get();
        bookToBorrow.setStatus("借出");
        bookRepository.save(bookToBorrow);

        // 創建借閱記錄
        BorrowingRecord record = new BorrowingRecord();
        record.setUserId(userId);
        record.setInventoryId(inventoryId);
        record.setBorrowingTime(new Date());
        // 預計還書時間和其他細節根據實際業務需求設定
        borrowingRecordRepository.save(record);

        return true;
    }

    public boolean returnBook(Long userId, Long inventoryId) {
        Optional<BorrowingRecord> record = borrowingRecordRepository.findByUserIdAndInventoryId(userId, inventoryId);
        if (record.isPresent() && record.get().getStatus().equals("借出")) {
            record.get().setStatus("已還");
            borrowingRecordRepository.save(record.get());

            Optional<Inventory> inventory = bookRepository.findById(inventoryId);
            if (inventory.isPresent()) {
                inventory.get().setStatus("可借閱");
                bookRepository.save(inventory.get());
                return true;
            }
        }
        return false;
    }
}
