package com.cloudmall.demo.thread;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class ThreadLocalDemo {
    private static ThreadLocal<Integer> id = new ThreadLocal<>();

    public static void main(String[] args) {
        ThreadPoolExecutor executor = new ThreadPoolExecutor(2,4,1, TimeUnit.SECONDS,new ArrayBlockingQueue<>(2));
        for(int i=1;i<=5;i++){
            int currentTaskId = i;
            executor.execute(new Runnable() {
                @Override
                public void run() {
                    if(currentTaskId<=3){
                        id.set(currentTaskId);
                    }
                    try{
                        System.out.printf(
                                "[%s] ThreadLocal taskId = %d%n",
                                Thread.currentThread().getName(),
                                id.get()
                        );
                    }finally {
                        id.remove();
                    }
                }
            });
        }
        executor.shutdown();
    }
}
