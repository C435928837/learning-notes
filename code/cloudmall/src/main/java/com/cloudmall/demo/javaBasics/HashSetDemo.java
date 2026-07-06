package com.cloudmall.demo.javaBasics;

import java.util.HashSet;
import java.util.Set;

public class HashSetDemo {
    public static void main(String[] args) {
        // 1. 创建一个 HashSet
        // 元素类型使用 String，表示用户 ID
        Set<String> set = new HashSet<>();
        // 2. 添加 U001
        set.add("U001");
        // 3. 添加 U002
        set.add("U002");

        // 4. 再次添加 U001
        set.add("U001");
        // 思考：为什么重复元素不会保存两份？
        //HashSet 用来去重，不保证插入顺序。
        // 5. 添加 U003
        set.add("U003");
        // 6. 输出 Set 的大小
        // 观察 U001 是否只算一次
        System.out.println(set.size());

        // 7. 使用 for-each 遍历 HashSet
        // 观察输出顺序是否等于插入顺序
        set.forEach(s -> {
            System.out.println(s);
        });

        // 8. 总结：
        // HashSet 用来去重
        // HashSet 不保证插入顺序
        // HashSet 底层主要依赖 HashMap
    }
}
