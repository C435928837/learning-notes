package com.cloudmall.demo.spring;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class OrderService {

    private final JdbcTemplate jdbcTemplate;

    public OrderService(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Transactional
    public void createOrder(String productId) {
        jdbcTemplate.update(
                "INSERT INTO orders(product_id) VALUES (?)",
                productId
        );

        jdbcTemplate.update(
                "UPDATE product_stock SET stock = stock - 1 WHERE product_id = ?",
                productId
        );

        throw new RuntimeException("模拟下单失败");
    }
}