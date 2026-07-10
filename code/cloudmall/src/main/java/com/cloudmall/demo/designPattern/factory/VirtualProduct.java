package com.cloudmall.demo.designPattern.factory;

public class VirtualProduct implements Product{
    @Override
    public void getName() {
        System.out.println("虚拟商品");
    }
}
