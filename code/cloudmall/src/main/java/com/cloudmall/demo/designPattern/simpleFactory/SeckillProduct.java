package com.cloudmall.demo.designPattern.simpleFactory;

public class SeckillProduct implements Product{
    private int price;
    @Override
    public void getName() {
        System.out.println("创建秒杀商品，需要校验活动时间和秒杀库存");
    }

    @Override
    public int getPrice() {
        return this.price;
    }

    @Override
    public void changePrice(int price) {
        this.price = price;
    }
}
