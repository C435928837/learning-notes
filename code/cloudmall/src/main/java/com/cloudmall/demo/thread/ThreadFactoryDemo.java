package com.cloudmall.demo.thread;

import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

public class ThreadFactoryDemo implements ThreadFactory {

    AtomicInteger threadNumber = new AtomicInteger(1);

    @Override
    public Thread newThread(Runnable r) {
        Thread thread = new Thread(r);
        thread.setName("cloudmall-product-async-" + threadNumber.getAndIncrement());
        return thread;
    }
}
