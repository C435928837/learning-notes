package com.cloudmall;

import com.cloudmall.demo.spring.ProductService;
import com.cloudmall.demo.spring.OrderService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.core.JdbcTemplate;

@SpringBootApplication
public class CloudMallApplication {
    public static void main(String[] args) {
        SpringApplication.run(CloudMallApplication.class, args);
    }

    @Bean
    CommandLineRunner run(ProductService productService) {
        return args -> System.out.println(productService.getProduct("P001"));
    }

    @Bean
    CommandLineRunner transactionDemo(OrderService orderService, JdbcTemplate jdbcTemplate) {
        return args -> {
            Integer orderCountBefore = jdbcTemplate.queryForObject(
                    "SELECT COUNT(*) FROM orders",
                    Integer.class
            );
            Integer availableStockBefore = jdbcTemplate.queryForObject(
                    "SELECT available_stock FROM product_stock WHERE product_id = ?",
                    Integer.class,
                    "P001"
            );
            Integer lockedStockBefore = jdbcTemplate.queryForObject(
                    "SELECT locked_stock FROM product_stock WHERE product_id = ?",
                    Integer.class,
                    "P001"
            );

            try {
                orderService.createOrder("P001");
            } catch (RuntimeException e) {
                System.out.println("捕获异常：" + e.getMessage());
            }

            Integer orderCount = jdbcTemplate.queryForObject(
                    "SELECT COUNT(*) FROM orders",
                    Integer.class
            );

            Integer stock = jdbcTemplate.queryForObject(
                    "SELECT available_stock FROM product_stock WHERE product_id = ?",
                    Integer.class,
                    "P001"
            );

            Integer lockedStock = jdbcTemplate.queryForObject(
                    "SELECT locked_stock FROM product_stock WHERE product_id = ?",
                    Integer.class,
                    "P001"
            );

            System.out.println("回滚前后订单数量：" + orderCountBefore + " -> " + orderCount);
            System.out.println("回滚前后 P001 可售库存：" + availableStockBefore + " -> " + stock);
            System.out.println("回滚前后 P001 锁定库存：" + lockedStockBefore + " -> " + lockedStock);
        };
    }

    @Bean
    CommandLineRunner conditionalStockDemo(OrderService orderService, JdbcTemplate jdbcTemplate) {
        return args -> {
            boolean firstDeduction = orderService.deductStock("P002");
            boolean secondDeduction = orderService.deductStock("P002");

            Integer stock = jdbcTemplate.queryForObject(
                    "SELECT available_stock FROM product_stock WHERE product_id = ?",
                    Integer.class,
                    "P002"
            );

            System.out.println("第一次条件扣库存：" + firstDeduction);
            System.out.println("第二次条件扣库存：" + secondDeduction);
            System.out.println("条件扣减后的 P002 可售库存：" + stock);
        };
    }

    @Bean
    CommandLineRunner pendingOrderDemo(OrderService orderService, JdbcTemplate jdbcTemplate) {
        return args -> {
            orderService.createPendingOrder("O202608160001", 1001L, "P001", 2);

            Integer availableStock = jdbcTemplate.queryForObject(
                    "SELECT available_stock FROM product_stock WHERE product_id = ?",
                    Integer.class,
                    "P001"
            );
            Integer lockedStock = jdbcTemplate.queryForObject(
                    "SELECT locked_stock FROM product_stock WHERE product_id = ?",
                    Integer.class,
                    "P001"
            );
            String orderStatus = jdbcTemplate.queryForObject(
                    "SELECT status FROM orders WHERE order_no = ?",
                    String.class,
                    "O202608160001"
            );
            String orderSnapshot = jdbcTemplate.queryForObject(
                    "SELECT product_name || ' / ' || product_price FROM orders WHERE order_no = ?",
                    String.class,
                    "O202608160001"
            );

            System.out.println("待支付订单状态：" + orderStatus);
            System.out.println("订单商品快照：" + orderSnapshot);
            System.out.println("P001 可售库存：" + availableStock);
            System.out.println("P001 锁定库存：" + lockedStock);

            try {
                orderService.createPendingOrder("O202608160002", 1001L, "P001", 100);
            } catch (RuntimeException e) {
                System.out.println("库存不足创建订单失败：" + e.getMessage());
            }

            Integer orderCount = jdbcTemplate.queryForObject(
                    "SELECT COUNT(*) FROM orders",
                    Integer.class
            );
            Integer availableStockAfterFailure = jdbcTemplate.queryForObject(
                    "SELECT available_stock FROM product_stock WHERE product_id = ?",
                    Integer.class,
                    "P001"
            );
            Integer lockedStockAfterFailure = jdbcTemplate.queryForObject(
                    "SELECT locked_stock FROM product_stock WHERE product_id = ?",
                    Integer.class,
                    "P001"
            );

            System.out.println("库存不足后的订单数量：" + orderCount);
            System.out.println("库存不足后的 P001 可售/锁定库存："
                    + availableStockAfterFailure + " / " + lockedStockAfterFailure);
        };
    }

    @Bean
    CommandLineRunner indexDemo(JdbcTemplate jdbcTemplate) {
        return args -> {
            String hitIndexPlan = jdbcTemplate.queryForObject(
                    """
                    EXPLAIN
                    SELECT order_no, status, created_at
                    FROM user_orders
                    WHERE user_id = 1001
                      AND status = 'PAID'
                    ORDER BY created_at DESC
                    """,
                    String.class
            );

            String missLeftPrefixPlan = jdbcTemplate.queryForObject(
                    """
                    EXPLAIN
                    SELECT order_no, status, created_at
                    FROM user_orders
                    WHERE status = 'PAID'
                    ORDER BY created_at DESC
                    """,
                    String.class
            );

            System.out.println("命中联合索引的计划：");
            System.out.println(hitIndexPlan);

            System.out.println("未遵守最左前缀的计划：");
            System.out.println(missLeftPrefixPlan);
        };
    }
}
