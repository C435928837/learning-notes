package com.cloudmall.demo.designPattern.simpleFactory;

public class NormalProduct implements Product{

    @Override
    public void create() {
        System.out.println("创建普通商品，需要库存和物流");
    }
}
