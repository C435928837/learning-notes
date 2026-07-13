package com.cloudmall.demo.designPattern.simpleFactory;

import java.util.*;

public class NormalProduct implements Product{
    private int price;
    private String code;
    private Long id;
    @Override
    public void getName() {
        System.out.println("创建普通商品，需要库存和物流");
    }

    @Override
    public int getPrice() {
        return this.price;
    }
    public String getCode() {
        return this.code;
    }

    @Override
    public void changePrice(int price) {
        this.price = price;
    }

    @Override
    public int hashCode() {
        return this.code.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if(this == obj){
            return true;
        }
        if(!(obj instanceof NormalProduct other)){
            return false;
        }
        return this.code.equals(other.code);
    }

    public NormalProduct(){};

    public NormalProduct(Long id,String code){
        this.id = id;
        this.code = code;
    }

    public static void main(String[] args) {
        NormalProduct product1 = new NormalProduct(1L,"code001");
        NormalProduct product2 = new NormalProduct(2L,"code001");
        NormalProduct product3 = new NormalProduct(3L,"code003");
        //LinkedList可以保证插入时顺序
        List<NormalProduct> productList = new LinkedList<>();
        productList.add(product1);
        productList.add(product2);
        productList.add(product3);
        for(NormalProduct p : productList){
            System.out.println(p.id);
        }
        //HashSet可以保证编号不重复
        Set<String> codeSet = new HashSet<>();
        codeSet.add(product1.code);
        codeSet.add(product2.code);
        codeSet.add(product3.code);
        for(String c : codeSet){
            System.out.println(c);
        }
        //Map通过id作为key、对象作为value，可通过key快速定位到value
        Map<Long,NormalProduct> productMap = new HashMap<>();
        productMap.put(product1.id,product1);
        productMap.put(product2.id,product2);
        productMap.put(product3.id,product3);
        System.out.println(productMap.get(3L).code);
        //Queue的数据结构是队列，数据输出是先进先出
        Queue<NormalProduct> productQueue = new ArrayDeque<>();
        productQueue.add(product1);
        productQueue.add(product2);
        productQueue.add(product3);
        System.out.println("排队输出：" + productQueue.poll().id);
        System.out.println("排队输出：" + productQueue.poll().id);
        System.out.println("排队输出：" + productQueue.poll().id);
    }
}
