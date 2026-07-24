package com.cloudmall.demo.thread;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class CompletableFutureDemo {

    public static String queryProductName(){
        try {
            System.out.printf("[%s] 查询商品名称%n", Thread.currentThread().getName());
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        return "iphone17";
    }

    public static int queryProductStock(){
        try {
            System.out.printf("[%s] 查询商品库存%n", Thread.currentThread().getName());
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        return 13;
    }

    public static int queryProductPrice(){
        try {
            System.out.printf("[%s] 查询商品价格%n", Thread.currentThread().getName());
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        return 13799;
    }

    public static void main(String[] args) {
        ThreadPoolExecutor executor = new ThreadPoolExecutor(3,4,1, TimeUnit.SECONDS,new ArrayBlockingQueue<>(5));
        try{
            long startTime = System.currentTimeMillis();
            CompletableFuture<String> name = CompletableFuture.supplyAsync(() -> {
                return queryProductName();
            },executor).exceptionally(exception -> {
                System.out.println(exception);
                return "获取商品失败";
            });
            CompletableFuture<Integer> stock = CompletableFuture.supplyAsync(() -> {
                return queryProductStock();
            },executor).handle((s,exception) -> {
                if(exception != null){
                    return 0;
                }
                return s;
            });
            CompletableFuture<Integer> price = CompletableFuture.supplyAsync(() -> {
                return queryProductPrice();
            },executor).handle((p,exception) -> {
                if(exception != null){
                    return 0;
                }
                return p;
            });
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
        }finally {
            executor.shutdown();
        }
    }
}
