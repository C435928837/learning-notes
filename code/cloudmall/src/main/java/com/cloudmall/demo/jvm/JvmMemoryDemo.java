package com.cloudmall.demo.jvm;

public class JvmMemoryDemo {
    //类变量：持有堆中 Product 对象的引用
    private static Product product;

    public static void main(String[] args) {
        product = createProduct();
        System.out.println(product.getName());
    }

    public static Product createProduct(){
        //name 引用和 stock 值位于当前栈帧的局部变量表
        String name = new String("商品A");
        int stock = 10;
        //Product 对象创建在堆中
        return new Product(name,stock);
    }

    static class Product{
        private String name;
        private int stock;

        public Product(String name,int stock){
            this.name = name;
            this.stock = stock;
        }

        public String getName(){
            return name;
        }
    }
}
