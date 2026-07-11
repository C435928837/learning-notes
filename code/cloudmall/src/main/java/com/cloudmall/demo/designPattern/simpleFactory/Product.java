package com.cloudmall.demo.designPattern.simpleFactory;

public interface Product {
    public void getName();

    public int getPrice();

    public void changePrice(int price);
}
