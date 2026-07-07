package com.cloudmall.demo.designPattern.simpleFactory;

public class VirtualProduct implements Product{
    @Override
    public void create() {
        System.out.println("创建虚拟商品，不需要物流，需要发放权益");
    }
}
