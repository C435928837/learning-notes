package com.cloudmall.demo.thread;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

public class ReentrantLockDemo {

    private static final ReentrantLock STOCK_LOCK = new ReentrantLock();
    private static final ReentrantLock DEMO_LOCK = new ReentrantLock();

    private static int stock = 10;
    private static int successfulOrders;

    public static void main(String[] args) throws InterruptedException {
        deductStockDemo();
        tryLockDemo();
        timedTryLockDemo();
        interruptibleLockDemo();
    }

    private static void deductStockDemo() throws InterruptedException {
        int threadCount = 20;
        CountDownLatch ready = new CountDownLatch(threadCount);
        CountDownLatch start = new CountDownLatch(1);
        CountDownLatch finished = new CountDownLatch(threadCount);

        for (int i = 1; i <= threadCount; i++) {
            new Thread(() -> {
                ready.countDown();
                await(start);
                deductStock();
                finished.countDown();
            }, "order-" + i).start();
        }

        ready.await();
        start.countDown();
        finished.await();

        System.out.println("=== 库存扣减 ===");
        System.out.println("初始库存：10，抢购线程数：" + threadCount);
        System.out.println("最终库存：" + stock);
        System.out.println("成功订单数：" + successfulOrders);
    }

    private static void deductStock() {
        STOCK_LOCK.lock();
        try {
            if (stock > 0) {
                stock--;
                successfulOrders++;
            }
        } finally {
            STOCK_LOCK.unlock();
        }
    }

    private static void tryLockDemo() throws InterruptedException {
        CountDownLatch locked = new CountDownLatch(1);
        Thread holder = holdDemoLock(1_000, locked);
        holder.start();
        locked.await();

        System.out.println("\n=== tryLock() ===");
        if (DEMO_LOCK.tryLock()) {
            try {
                System.out.println("tryLock 获取锁成功");
            } finally {
                DEMO_LOCK.unlock();
            }
        } else {
            System.out.println("tryLock 获取锁失败：立即执行降级处理");
        }

        holder.join();
    }

    private static void timedTryLockDemo() throws InterruptedException {
        CountDownLatch locked = new CountDownLatch(1);
        Thread holder = holdDemoLock(1_500, locked);
        holder.start();
        locked.await();

        System.out.println("\n=== tryLock(timeout) ===");
        boolean acquired = false;
        try {
            acquired = DEMO_LOCK.tryLock(300, TimeUnit.MILLISECONDS);
            if (acquired) {
                System.out.println("超时 tryLock 获取锁成功");
            } else {
                System.out.println("等待 300ms 后仍未获取锁，放弃本次操作");
            }
        } finally {
            if (acquired) {
                DEMO_LOCK.unlock();
            }
        }

        holder.join();
    }

    private static void interruptibleLockDemo() throws InterruptedException {
        CountDownLatch locked = new CountDownLatch(1);
        Thread holder = holdDemoLock(1_500, locked);
        holder.start();
        locked.await();

        System.out.println("\n=== lockInterruptibly() ===");
        Thread waiter = new Thread(() -> {
            boolean acquired = false;
            try {
                DEMO_LOCK.lockInterruptibly();
                acquired = true;
                System.out.println("可中断线程获取锁成功");
            } catch (InterruptedException e) {
                System.out.println("等待锁时收到中断，放弃等待");
                Thread.currentThread().interrupt();
            } finally {
                if (acquired) {
                    DEMO_LOCK.unlock();
                }
            }
        }, "interruptible-waiter");

        waiter.start();
        Thread.sleep(300);
        waiter.interrupt();

        waiter.join();
        holder.join();
    }

    private static Thread holdDemoLock(long sleepMillis, CountDownLatch locked) {
        return new Thread(() -> {
            DEMO_LOCK.lock();
            try {
                locked.countDown();
                Thread.sleep(sleepMillis);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            } finally {
                DEMO_LOCK.unlock();
            }
        }, "lock-holder");
    }

    private static void await(CountDownLatch latch) {
        try {
            latch.await();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}