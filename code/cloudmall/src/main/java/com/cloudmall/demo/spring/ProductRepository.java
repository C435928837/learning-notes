package com.cloudmall.demo.spring;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;


@Repository
public class ProductRepository {
    private final JdbcTemplate jdbcTemplate;

    public ProductRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public String getProductById(String productId){
        return productId + "商品";
    }

    public boolean existsByCode(String productCode) {
        Integer count = jdbcTemplate.queryForObject(
                "SELECT COUNT(*) FROM products WHERE product_code = ?",
                Integer.class,
                productCode
        );
        return count != null && count > 0;
    }

    public void save(CreateProductRequest request) {
        jdbcTemplate.update(
                """
                INSERT INTO products(product_code, product_name, price)
                VALUES (?, ?, ?)
                """,
                request.productCode(),
                request.productName(),
                request.price()
        );
    }
}
