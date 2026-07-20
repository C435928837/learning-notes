package com.cloudmall.demo.thread;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class ThreadPoolDemo {
    public static void main(String[] args) throws InterruptedException {
        ThreadPoolExecutor executor = new ThreadPoolExecutor(2,
                4,
                60,
                TimeUnit.SECONDS,
                new ArrayBlockingQueue<>(2),
                new ThreadFactoryDemo(),
                new ThreadPoolExecutor.CallerRunsPolicy());

        for (int taskId = 1; taskId <= 7; taskId++) {
            int currentTaskId = taskId;

            executor.execute(() -> processProductTask(currentTaskId));

            System.out.printf(
                    "提交任务 %d，活跃线程数：%d，队列任务数：%d%n",
                    currentTaskId,
                    executor.getActiveCount(),
                    executor.getQueue().size()
            );
        }

        executor.shutdown();
        executor.awaitTermination(30, TimeUnit.SECONDS);

        System.out.println("所有商品任务处理完成");
    }

    private static void processProductTask(int taskId) {
        String threadName = Thread.currentThread().getName();

        System.out.printf(
                "[%s] 开始处理商品任务 %d%n",
                threadName,
                taskId
        );

        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            System.out.printf(
                    "[%s] 商品任务 %d 被中断%n",
                    threadName,
                    taskId
            );
            return;
        }

        System.out.printf(
                "[%s] 完成商品任务 %d%n",
                threadName,
                taskId
        );
    }
}
