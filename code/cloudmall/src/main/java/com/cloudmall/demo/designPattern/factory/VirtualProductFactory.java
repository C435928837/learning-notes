package com.cloudmall.demo.designPattern.factory;

public class VirtualProductFactory implements ProductFactory{
    @Override
    public Product createProduct() {
        return new VirtualProduct();
    }
}
