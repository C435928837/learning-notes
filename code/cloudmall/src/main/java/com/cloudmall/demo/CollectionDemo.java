package com.cloudmall.demo;

import java.util.ArrayList;
import java.util.Collection;

public class CollectionDemo {
    public static void main(String[] args) {
        // 1. 创建一个 Collection 集合
        // 提示：Collection 是接口，不能直接 new
        Collection collection = new ArrayList();
        // 可以用 ArrayList 作为实现类

        // 2. 向集合中添加 3 个元素
        // 例如：Java、Spring、HashMap
        collection.add("Java");
        collection.add("Spring");
        collection.add("HashMap");
        // 3. 输出集合大小
        // 观察 size() 的结果
        System.out.println(collection.size());

        // 4. 判断集合中是否包含某个元素
        // 例如判断是否包含 Java
        System.out.println(collection.contains("Spring"));

        // 5. 删除一个元素
        // 例如删除 Spring
        collection.remove("Spring");

        // 6. 使用 for-each 遍历集合
        collection.forEach(o -> {
            System.out.println(o);
        });
        // 思考：为什么 Collection 可以被 for-each 遍历？
        // Collection继承了Iterable，Iterable要求实现类必须能返回一个Iterator。Iterator通过调用hasNext()判断有没有next，再调用Next()获得下一个对象，如此类推实现循环。
    }
}
