package com.cloudmall.demo.spring;

import java.math.BigDecimal;

import org.springframework.http.HttpStatus;
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
        createPendingOrder("ROLLBACK-" + productId, 1001L, productId, 1);
        throw new RuntimeException("模拟下单失败");
    }

    /**
     * 单条条件更新同时完成库存校验与扣减，返回 false 表示库存不足或商品不存在。
     */
    @Transactional
    public boolean deductStock(String productId) {
        int affectedRows = jdbcTemplate.update(
                "UPDATE product_stock SET available_stock = available_stock - 1 "
                        + "WHERE product_id = ? AND available_stock > 0",
                productId
        );
        return affectedRows == 1;
    }

    /**
     * 创建待支付订单：锁定可售库存，并保存商品名称和成交价快照。
     */
    @Transactional
    public void createPendingOrder(String orderNo, long userId, String productId, int quantity) {
        if (quantity <= 0) {
            throw new IllegalArgumentException("购买数量必须大于 0");
        }

        ProductSnapshot snapshot = jdbcTemplate.queryForObject(
                "SELECT product_name, price FROM products WHERE product_code = ?",
                (resultSet, rowNum) -> new ProductSnapshot(
                        resultSet.getString("product_name"),
                        resultSet.getBigDecimal("price")
                ),
                productId
        );

        int affectedRows = jdbcTemplate.update(
                """
                UPDATE product_stock
                SET available_stock = available_stock - ?,
                    locked_stock = locked_stock + ?
                WHERE product_id = ?
                  AND available_stock >= ?
                """,
                quantity,
                quantity,
                productId,
                quantity
        );

        if (affectedRows != 1) {
            throw new BusinessException(40902, HttpStatus.CONFLICT, "库存不足");
        }

        jdbcTemplate.update(
                """
                INSERT INTO orders(
                    order_no, user_id, product_id, product_name, product_price,
                    quantity, total_amount, status
                ) VALUES (?, ?, ?, ?, ?, ?, ?, ?)
                """,
                orderNo,
                userId,
                productId,
                snapshot.productName(),
                snapshot.productPrice(),
                quantity,
                snapshot.productPrice().multiply(BigDecimal.valueOf(quantity)),
                "PENDING_PAYMENT"
        );
    }

    private record ProductSnapshot(String productName, BigDecimal productPrice) {
    }
}
