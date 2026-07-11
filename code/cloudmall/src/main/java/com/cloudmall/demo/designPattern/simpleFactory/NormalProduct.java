package com.cloudmall.demo.designPattern.simpleFactory;

public class NormalProduct implements Product{
    private int price;
    @Override
    public void getName() {
        System.out.println("创建普通商品，需要库存和物流");
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
