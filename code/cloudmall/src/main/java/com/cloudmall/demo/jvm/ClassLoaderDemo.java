package com.cloudmall.demo.jvm;

public class ClassLoaderDemo {
    public static void main(String[] args) {
        ClassLoader appLoader = ClassLoaderDemo.class.getClassLoader();

        System.out.println("Bootstrap（String）: " + String.class.getClassLoader());
        System.out.println("Application: " + appLoader);
        System.out.println("Context: " + Thread.currentThread().getContextClassLoader());
        System.out.println("Platform: " + appLoader.getParent());
        System.out.println("Bootstrap（Platform 的父加载器）: "
                + appLoader.getParent().getParent());
    }
}
