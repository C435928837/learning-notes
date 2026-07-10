package com.cloudmall.demo.designPattern.factory;

public class SeckillProduct implements Product{
    @Override
    public void getName() {
        System.out.println("秒杀商品");
    }
}
