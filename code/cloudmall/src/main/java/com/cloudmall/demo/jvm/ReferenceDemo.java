package com.cloudmall.demo.jvm;

import java.lang.ref.PhantomReference;
import java.lang.ref.Reference;
import java.lang.ref.ReferenceQueue;
import java.lang.ref.SoftReference;
import java.lang.ref.WeakReference;

public class ReferenceDemo {

    public static void main(String[] args) throws InterruptedException {
        demonstrateStrongReference();
        demonstrateSoftReference();
        demonstrateWeakReference();
        demonstratePhantomReference();
    }

    private static void demonstrateStrongReference() throws InterruptedException {
        Product product = new Product("P001");
        System.gc();
        Thread.sleep(100);

        System.out.println("强引用：" + product);
    }

    private static void demonstrateSoftReference() throws InterruptedException {
        SoftReference<Product> softReference =
                new SoftReference<>(new Product("P002"));

        System.out.println("软引用，GC 前：" + softReference.get());

        System.gc();
        Thread.sleep(100);

        System.out.println("软引用，GC 后：" + softReference.get());
    }

    private static void demonstrateWeakReference() throws InterruptedException {
        Product product = new Product("P003");
        WeakReference<Product> weakReference = new WeakReference<>(product);

        System.out.println("弱引用，GC 前：" + weakReference.get());

        product = null;
        waitForWeakReferenceCleared(weakReference);

        System.out.println("弱引用，GC 后：" + weakReference.get());
    }

    private static void demonstratePhantomReference() throws InterruptedException {
        ReferenceQueue<Product> queue = new ReferenceQueue<>();

        Product product = new Product("P004");
        PhantomReference<Product> phantomReference =
                new PhantomReference<>(product, queue);

        System.out.println("虚引用 get()：" + phantomReference.get());

        product = null;

        Reference<? extends Product> enqueuedReference = null;
        for (int i = 0; i < 10 && enqueuedReference == null; i++) {
            System.gc();
            enqueuedReference = queue.remove(200);
        }

        System.out.println("虚引用是否进入引用队列："
                + (enqueuedReference == phantomReference));
    }

    private static void waitForWeakReferenceCleared(
            WeakReference<Product> weakReference) throws InterruptedException {

        for (int i = 0; i < 10 && weakReference.get() != null; i++) {
            System.gc();
            Thread.sleep(100);
        }
    }

    private static class Product {
        private final String code;

        private Product(String code) {
            this.code = code;
        }

        @Override
        public String toString() {
            return "Product{code='" + code + "'}";
        }
    }
}