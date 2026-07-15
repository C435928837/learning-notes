package com.cloudmall.demo.thread;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class VolatileDemo {
    private static volatile boolean running = true;
    private static volatile int stock = 10;

    static class ThreadTask implements Runnable{
        @Override
        public void run() {
            int i = 1;
            while(running){
                System.out.println(i++);
            }
            System.out.println("线程已停止");
        }
    }

    public static void reduceStock(String threadName){
        if(stock > 0){
            System.out.println(threadName + "已经入方法，当前库存：" + stock);
            stock--;
        } else {
            System.out.println(threadName + "已经入方法，当前库存已清空" );
        }
    }

    public static void main(String[] args) throws InterruptedException {
        Thread thread = new Thread(new ThreadTask());
        thread.start();
        Thread.sleep(1000);
        running = false;

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
    }

}
