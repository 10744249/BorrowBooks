package com.example.demo;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

@Component
public class DataSourceTestRunner implements CommandLineRunner {

    private final DataSource dataSource;

    public DataSourceTestRunner(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public void run(String... args) throws Exception {
        try (Connection connection = dataSource.getConnection()) {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT 1"); // 執行一個簡單的查詢來測試連接
            if (resultSet.next()) {
                System.out.println("資料庫連接成功！");
            } else {
                System.out.println("資料庫連接失敗！");
            }
        }
    }
}
