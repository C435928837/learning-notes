package com.cloudmall.demo.javaBasics;

import java.util.HashMap;
import java.util.Map;

public class HashMapDemo {
    public static void main(String[] args) {
        // 1. 创建一个 HashMap
        // key 使用 String，表示商品名
        // value 使用 Integer，表示库存数量
        Map<String,Integer> map = new HashMap<>();

        // 2. 放入 apple -> 10
        map.put("apple",10);
        // 3. 放入 banana -> 20
        map.put("banana",20);

        // 4. 再次放入 apple -> 30
        map.put("apple",30);
        // 思考：为什么 apple 的值会被覆盖？
        //HashMap 存 key-value，key 不重复，重复 put 会覆盖。
        // 5. 根据 key 获取 apple 的库存
        System.out.println(map.get("apple"));
        // 6. 判断是否包含 apple 这个 key
        System.out.println(map.containsKey("apple"));
        // 7. 使用 entrySet 遍历 HashMap
        // 输出 key 和 value
        for(Map.Entry<String,Integer> m : map.entrySet()){
            System.out.println("key:"+m.getKey());
            System.out.println("value:"+m.getValue());
        }

        // 8. 总结：
        // HashMap 存储 key-value
        // key 不能重复
        // value 可以重复
    }
}
