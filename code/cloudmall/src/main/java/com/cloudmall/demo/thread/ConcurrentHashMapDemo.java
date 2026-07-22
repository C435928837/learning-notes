package com.cloudmall.demo.thread;

import java.util.concurrent.*;
import java.util.concurrent.atomic.LongAdder;

public class ConcurrentHashMapDemo {
    public static void main(String[] args) {
        Product productA = new Product("ProductA");
        Product productB = new Product("ProductB");
        Runnable taskA = (productA::setClick);
        Runnable taskB = (productB::setClick);
        ThreadPoolExecutor executor = new ThreadPoolExecutor(2,4,1, TimeUnit.SECONDS,new ArrayBlockingQueue<>(30));
        for(int i=0;i<20;i++){
            if(i>=9){
                executor.execute(taskB);
            }
            executor.execute(taskA);
        }
        executor.shutdown();
        try {
            if (!executor.awaitTermination(5, TimeUnit.SECONDS)) {
                executor.shutdownNow();
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        System.out.println("ProductA的浏览次数：" + productA.getClick());
        System.out.println("ProductB的浏览次数：" + productB.getClick());

    }

    static class Product{
        private final String name;
        private final ConcurrentMap<String,LongAdder> click;

        public Product(String name){
            this.name = name;
            click = new ConcurrentHashMap<>();
        }

        public void setClick(){
            click.computeIfAbsent(name,key -> new LongAdder()).increment();
        }

        public long getClick(){
            LongAdder longAdder = click.get(name);
            return longAdder == null ? 0 : longAdder.sum();
        }
    }
}
