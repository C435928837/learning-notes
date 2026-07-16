package com.cloudmall.demo.thread;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

public class AtomicIntegerDemo {
    private static final  AtomicInteger stock = new AtomicInteger(10);
    private static int order = 1;

    public static void reduceStock(String threadName){
        while(true){
            int oldValue = stock.get();
            if (oldValue <= 0) {
                break;
            }

            if (stock.compareAndSet(oldValue, oldValue - 1)) {
                order++;
                break;
            }
        }

    }

    public static void main(String[] args) throws InterruptedException {
        System.out.println("初始库存：10");
        System.out.println("线程数量：20");
        int threadCount = 20;
        ExecutorService executor = Executors.newFixedThreadPool(threadCount);
        CountDownLatch readyLatch = new CountDownLatch(threadCount);
        CountDownLatch startLatch = new CountDownLatch(1);
        CountDownLatch finishLatch =  new CountDownLatch(threadCount);
        for (int i = 1; i <= threadCount; i++) {
            final int taskNumber = i;
            executor.submit(() -> {
                try {
                    // 告诉主线程：当前线程已经准备好
                    readyLatch.countDown();
                    // 所有线程在这里等待
                    startLatch.await();
                    reduceStock("线程" + taskNumber);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                } finally {
                    finishLatch.countDown();
                }
            });
        }
        // 等待所有线程准备完成
        readyLatch.await();
        System.out.println("所有线程开始竞争库存");
        // 同时放行所有线程
        startLatch.countDown();
        // 等待所有任务结束
        finishLatch.await();
        executor.shutdown();
        System.out.println("最终库存：" + stock.get());
        System.out.println("成功订单数：" + order);
    }
}
