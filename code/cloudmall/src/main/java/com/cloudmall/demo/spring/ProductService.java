package com.cloudmall.demo.spring;

import org.springframework.stereotype.Service;

@Service
public class ProductService {

    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository){
        this.productRepository = productRepository;
    }

    public String getProduct(String productId) throws InterruptedException {
        Thread.sleep(3000);
        return productRepository.getProductById(productId);
    }

}
