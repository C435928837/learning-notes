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
                    "SELECT stock FROM product_stock WHERE product_id = ?",
                    Integer.class,
                    "P001"
            );

            System.out.println("订单数量：" + orderCount);
            System.out.println("P001 库存：" + stock);
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
