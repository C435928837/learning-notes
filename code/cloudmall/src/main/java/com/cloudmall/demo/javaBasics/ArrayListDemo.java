package com.cloudmall.demo.javaBasics;


import com.cloudmall.demo.designPattern.simpleFactory.NormalProduct;
import com.cloudmall.demo.designPattern.simpleFactory.Product;

import java.util.ArrayList;
import java.util.List;

public class ArrayListDemo {
    public static void main(String[] args) {
        NormalProduct normalProduct1 = new NormalProduct(1L,"code1");
        NormalProduct normalProduct2 = new NormalProduct(2L,"code2");
        NormalProduct normalProduct3 = new NormalProduct(3L,"code3");
        List<NormalProduct> productList = new ArrayList<>();
        productList.add(normalProduct1);
        for(NormalProduct p : productList){
            System.out.println(p.getCode());
        }
        productList.add(normalProduct2);
        for(NormalProduct p : productList){
            System.out.println(p.getCode());
        }
        productList.add(1,normalProduct3);
        for(NormalProduct p : productList){
            System.out.println(p.getCode());
        }
        productList.remove(1);
        for(NormalProduct p : productList){
            System.out.println(p.getCode());
        }
        System.out.println(productList.get(1).getCode());
    }


}
