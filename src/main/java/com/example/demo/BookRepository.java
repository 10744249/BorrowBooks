package com.example.demo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookRepository extends JpaRepository<Inventory, Long> {
    // 這裡可以根據需要添加自定義查詢方法
}
