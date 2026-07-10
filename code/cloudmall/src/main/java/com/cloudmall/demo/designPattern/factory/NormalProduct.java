package com.cloudmall.demo.designPattern.factory;

public class NormalProduct implements Product{
    @Override
    public void getName() {
        System.out.println("普通商品");
    }
}
