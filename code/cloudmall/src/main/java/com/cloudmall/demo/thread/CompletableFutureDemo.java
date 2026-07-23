package com.cloudmall.demo.thread;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class CompletableFutureDemo {
    private static String productName;
    private static int productStock;
    private static int productPrice;

    public static String queryProductName(){
        productName = "iphone17";
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        return productName;
    }

    public static int queryProductStock(){
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        productStock = 13;
        return productStock;
    }

    public static int queryProductPrice(){
        productPrice = 13799;
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        return productPrice;
    }

    public static void main(String[] args) {
        ThreadPoolExecutor executor = new ThreadPoolExecutor(2,4,1, TimeUnit.SECONDS,new ArrayBlockingQueue<>(5));
        long startTime = System.currentTimeMillis();
        CompletableFuture<String> name = CompletableFuture.supplyAsync(() -> {
            return queryProductName();
        },executor).exceptionally(exception -> {
            System.out.println(exception);
            return "获取商品失败";
        });
        CompletableFuture<Integer> stock = CompletableFuture.supplyAsync(() -> {
            return queryProductStock();
        },executor);
        CompletableFuture<Integer> price = CompletableFuture.supplyAsync(() -> {
            return queryProductPrice();
        },executor);
        CompletableFuture<Void> allFuture = CompletableFuture.allOf(name,stock,price);
        CompletableFuture<String> detail = allFuture.thenApply(unused -> {
            String product_name = name.join();
            int product_stock = stock.join();
            int product_price = price.join();
            return "商品名称：" + product_name + "，价格为：" + product_price + "，库存为：" + product_stock;
        });
        System.out.println("并行结果" + detail.join());
        long endTime = System.currentTimeMillis();
        System.out.println("并行耗时："+ (endTime - startTime));

        long start = System.currentTimeMillis();
        queryProductName();
        queryProductStock();
        queryProductPrice();
        long end = System.currentTimeMillis();
        System.out.println("串行耗时："+ (end - start));
    }
}
