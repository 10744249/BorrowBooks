package com.example.demo;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Column;
import java.util.Date;

@Entity
@Table(name = "borrowing_record")
public class BorrowingRecord {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_id")
    private Long userId; // 用戶ID

    @Column(name = "inventory_id")
    private Long inventoryId; // 庫存ID

    @Column(name = "borrowing_time")
    private Date borrowingTime; // 借閱時間

    @Column(name = "status")
    private String status; // 新增：借閱記錄的狀態（例如："借出"、"已還"）

    // Getters 和 Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getInventoryId() {
        return inventoryId;
    }

    public void setInventoryId(Long inventoryId) {
        this.inventoryId = inventoryId;
    }

    public Date getBorrowingTime() {
        return borrowingTime;
    }

    public void setBorrowingTime(Date borrowingTime) {
        this.borrowingTime = borrowingTime;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
