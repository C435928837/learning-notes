package com.cloudmall.demo.thread;

import java.util.concurrent.*;

public class ThreadCreationDemo {

    static class ThreadDemo extends Thread{
        @Override
        public void run() {
            System.out.println("Thread任务：Thread线程");
        }
    }

    public static void main(String[] args) {
        ThreadDemo threadDemo = new ThreadDemo();
        threadDemo.start();

        Thread runnable = new Thread(() -> {
            System.out.println("Runnable任务：Runnable线程");
        });
        runnable.start();

        Callable<Integer> callable = () -> {
            int result = 10 + 20;
            return result;
        };
        ExecutorService executor = Executors.newFixedThreadPool(2);
        Future<Integer> future = executor.submit(callable);
        try {
            Integer result = future.get();
            System.out.println("Callable结果：" + result);
        }catch(Exception e){
            System.out.println(e);
        } finally {
            executor.shutdown();
        }

    }
}
