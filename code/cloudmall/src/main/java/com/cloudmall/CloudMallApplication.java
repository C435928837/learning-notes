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
}
