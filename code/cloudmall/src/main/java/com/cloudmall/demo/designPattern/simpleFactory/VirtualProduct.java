package com.cloudmall.demo.designPattern.simpleFactory;

public class VirtualProduct implements Product{
    private int price;

    @Override
    public void getName() {
        System.out.println("创建虚拟商品，不需要物流，需要发放权益");
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
