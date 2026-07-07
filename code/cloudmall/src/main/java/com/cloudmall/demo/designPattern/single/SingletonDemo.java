package com.cloudmall.demo.designPattern.single;

public class SingletonDemo {

    public static void main(String[] args) {
        // 1. 获取饿汉式单例对象两次
        HungrySingleton hungrySingleton1 = HungrySingleton.getInstance();
        HungrySingleton hungrySingleton2 = HungrySingleton.getInstance();
        // 2. 比较两次获取到的对象是否是同一个
        System.out.println(hungrySingleton1 == hungrySingleton2);
        // 3. 获取懒汉式单例对象两次
        LazySingleton lazySingleton1 = LazySingleton.getInstance();
        LazySingleton lazySingleton2 = LazySingleton.getInstance();
        // 4. 比较两次获取到的对象是否是同一个
        System.out.println(lazySingleton1 == lazySingleton2);
        // 5. 获取双重检查锁单例对象两次
        DoubleCheckSingleton doubleCheckSingleton1 = DoubleCheckSingleton.getInstance();
        DoubleCheckSingleton doubleCheckSingleton2 = DoubleCheckSingleton.getInstance();
        // 6. 比较两次获取到的对象是否是同一个
        System.out.println(doubleCheckSingleton1 == doubleCheckSingleton2);
        // 7. 获取静态内部类单例对象两次
        HolderSingleton holderSingleton1 = HolderSingleton.getInstance();
        HolderSingleton holderSingleton2 = HolderSingleton.getInstance();
        // 8. 比较两次获取到的对象是否是同一个
        System.out.println(holderSingleton1 == holderSingleton2);
        // 9. 输出总结：
        // 饿汉式：类加载时创建，线程安全，但不是懒加载
        // 懒汉式：用到时创建，但普通写法线程不安全
        // 双重检查锁：volatile + synchronized，线程安全且性能较好
        // 静态内部类：利用 JVM 类加载机制，线程安全且懒加载
    }
}

/**
 * 饿汉式单例
 *
 * 要求：
 * 1. 构造方法私有化
 * 2. 类加载时直接创建对象
 * 3. 提供 public static 方法返回对象
 */
class HungrySingleton {

    // 1. 定义 private static final 的唯一实例
    private static final HungrySingleton hungrySingleton = new HungrySingleton();
    // 2. 私有化构造方法
    private HungrySingleton(){};
    // 3. 提供 getInstance 方法
    public static HungrySingleton getInstance(){
        return hungrySingleton;
    }
}

/**
 * 懒汉式单例
 *
 * 要求：
 * 1. 构造方法私有化
 * 2. 一开始不创建对象
 * 3. 第一次调用 getInstance 时才创建对象
 *
 * 注意：
 * 这个版本线程不安全，只用于理解懒加载思想。
 */
class LazySingleton {

    // 1. 定义 private static 实例变量，先不初始化
    private static LazySingleton lazySingleton;
    // 2. 私有化构造方法
    private LazySingleton(){};
    // 3. 提供 getInstance 方法
    // 如果 instance == null，则创建对象
    // 否则直接返回已有对象
    public static LazySingleton getInstance(){
        if(lazySingleton == null){
            lazySingleton = new LazySingleton();
        }
        return lazySingleton;
    }
}

/**
 * 双重检查锁单例
 *
 * 要求：
 * 1. 构造方法私有化
 * 2. instance 使用 volatile 修饰
 * 3. 第一层 if 避免每次都加锁
 * 4. synchronized 保证创建过程线程安全
 * 5. 第二层 if 防止多个线程重复创建对象
 */
class DoubleCheckSingleton {

    // 1. 定义 private static volatile 实例变量
    private static volatile DoubleCheckSingleton doubleCheckSingleton;
    // 2. 私有化构造方法
    private DoubleCheckSingleton(){};
    // 3. 提供 getInstance 方法
    public static DoubleCheckSingleton getInstance(){
        // 4. 第一层判断 instance 是否为 null
        if(doubleCheckSingleton == null){
            // 5. synchronized 锁住当前 Class 对象
            synchronized(DoubleCheckSingleton.class){
                // 6. 第二层判断 instance 是否为 null
                if(doubleCheckSingleton == null){
                    // 7. 创建对象
                    doubleCheckSingleton = new DoubleCheckSingleton();
                }
            }
        }
        // 8. 返回对象
        return doubleCheckSingleton;
    }
}

/**
 * 静态内部类单例
 *
 * 要求：
 * 1. 构造方法私有化
 * 2. 定义一个私有静态内部类
 * 3. 在内部类中创建唯一实例
 * 4. 外部类 getInstance 返回内部类中的实例
 *
 * 原理：
 * 外部类加载时，不会立即加载内部类。
 * 只有调用 getInstance 时，才会加载内部类。
 * JVM 保证类加载过程线程安全。
 */
class HolderSingleton {
    // 1. 私有化构造方法
    private HolderSingleton(){};
    // 2. 定义静态内部类 SingletonHolder
    static class SingletonHolder{
        // 3. 在内部类中定义 private static final 实例
        private static final HolderSingleton holderSingleton = new HolderSingleton();
    }
    // 4. 提供 getInstance 方法，返回 SingletonHolder 中的实例
    public static HolderSingleton getInstance(){
        return SingletonHolder.holderSingleton;
    }
}