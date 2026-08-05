package com.cloudmall.demo.spring;

import org.springframework.stereotype.Repository;


@Repository
public class ProductRepository {
    public String getProductById(String productId){
        return productId + "商品";
    }
}
