package com.cloudmall.demo.designPattern.factory;

public class NormalProductFactory implements ProductFactory{
    @Override
    public Product createProduct() {
        return new NormalProduct();
    }
}
