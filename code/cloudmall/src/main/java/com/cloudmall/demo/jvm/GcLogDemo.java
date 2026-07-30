package com.cloudmall.demo.jvm;

public class GcLogDemo {
    public static void main(String[] args) throws InterruptedException {
        for (int i = 0; i < 100; i++) {
            byte[] data = new byte[1024 * 1024]; // 每次分配 1MB
            Thread.sleep(20);
        }

        System.out.println("执行结束");
    }
}
