package com.cloudmall.demo.designPattern.simpleFactory;

public class ProductFactory {

    public static Product createProduct(String type) {
        if ("NORMAL".equals(type)) {
            return new NormalProduct();
        }

        if ("VIRTUAL".equals(type)) {
            return new VirtualProduct();
        }

        if ("SECKILL".equals(type)) {
            return new SeckillProduct();
        }

        throw new IllegalArgumentException("不支持的商品类型");
    }

}
