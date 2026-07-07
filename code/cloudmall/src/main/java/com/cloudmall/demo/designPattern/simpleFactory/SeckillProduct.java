package com.cloudmall.demo.designPattern.simpleFactory;

public class SeckillProduct implements Product{
    @Override
    public void create() {
        System.out.println("创建秒杀商品，需要校验活动时间和秒杀库存");
    }
}
