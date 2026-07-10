package com.cloudmall.demo.designPattern.factory;

public class FactoryMethodDemo {
    public static void main(String[] args) {
        ProductFactory productFactory = new NormalProductFactory();
        Product product = productFactory.createProduct();
        product.getName();
    }
}
