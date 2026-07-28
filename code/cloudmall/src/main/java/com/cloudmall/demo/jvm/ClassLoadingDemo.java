package com.cloudmall.demo.jvm;

public class ClassLoadingDemo {

    public static void main(String[] args) throws ClassNotFoundException {
        //调用编译期常量的静态变量，不会触发Child初始化
        System.out.println(Child.MAX);
        //访问Parent的非编译期静态字段，只触发Parent的初始化
        System.out.println(Child.value);
        //访问Child的非编译期静态字段，先触发Parent的初始化，再触发Child的初始化
        System.out.println(Child.childValue);
        //通过Class.forname()触发Animal的初始化
        Class<?> animal =  Class.forName(Animal.class.getName());
    }

    static class Parent{
        static int value = 10;

        static {
            System.out.println("Parent 初始化");
        }
    }

    static class Child extends Parent{
        static int childValue = 20;
        static final int MAX = 8;

        static {
            System.out.println("Child 初始化");
        }
    }

    static class Animal{
        static {
            System.out.println("Animal 初始化");
        }
    }

}
