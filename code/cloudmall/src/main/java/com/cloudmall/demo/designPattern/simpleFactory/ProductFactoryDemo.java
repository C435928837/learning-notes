package com.cloudmall.demo.designPattern.simpleFactory;

public class ProductFactoryDemo {
    public static void main(String[] args) {
        Product product = ProductFactory.createProduct("NORMAL");
        product.create();
    }
}
