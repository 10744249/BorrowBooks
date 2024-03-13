package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class ControllerClass {

    private final UserServiceClass userService;
    private final BookService bookService; // 新增引用BookService

    @Autowired
    public ControllerClass(UserServiceClass userService, BookService bookService) {
        this.userService = userService;
        this.bookService = bookService; // 初始化BookService
    }

    // 處理用戶註冊請求
    @PostMapping("/register")
    public ResponseEntity<String> registerUser(@RequestBody User user) {
        boolean isRegistered = userService.registerUser(user);
        if (isRegistered) {
            return ResponseEntity.ok("用戶註冊成功");
        } else {
            return ResponseEntity.badRequest().body("註冊失敗，手機號碼可能已被使用");
        }
    }

    // 處理用戶登入請求
    @PostMapping("/login")
    public ResponseEntity<String> loginUser(@RequestBody User user) {
        boolean isAuthenticated = userService.authenticateUser(user);
        if (isAuthenticated) {
            return ResponseEntity.ok("用戶身份驗證成功");
        } else {
            return ResponseEntity.status(401).body("身份驗證失敗");
        }
    }

    // 處理借書請求
    @PostMapping("/borrow")
    public ResponseEntity<String> borrowBook(@RequestBody BorrowRequest borrowRequest) {
        boolean success = bookService.borrowBook(borrowRequest.getUserId(), borrowRequest.getInventoryId());
        if (success) {
            return ResponseEntity.ok("書籍借閱成功。");
        } else {
            return ResponseEntity.badRequest().body("書籍借閱失敗，可能是因為書籍已被借出或不存在。");
        }
    }

    // 處理還書請求
    @PostMapping("/return")
    public ResponseEntity<String> returnBook(@RequestBody BorrowRequest returnRequest) {
        boolean success = bookService.returnBook(returnRequest.getUserId(), returnRequest.getInventoryId());
        if (success) {
            return ResponseEntity.ok("書籍成功歸還。");
        } else {
            return ResponseEntity.badRequest().body("書籍歸還失敗，可能是因為找不到對應的借閱記錄或書籍狀態問題。");
        }
    }

    // 用於接收借書和還書請求的數據結構
    private static class BorrowRequest {
        private Long userId;
        private Long inventoryId;

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
    }

    // 其他控制器方法...
}
