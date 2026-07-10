package com.cloudmall.demo.designPattern.factory;

public class SeckillProductFactory implements ProductFactory{
    @Override
    public Product createProduct() {
        return new SeckillProduct();
    }
}
