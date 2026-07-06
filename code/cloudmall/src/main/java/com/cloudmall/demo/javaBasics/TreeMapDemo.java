package com.cloudmall.demo.javaBasics;

import java.util.Map;
import java.util.TreeMap;

public class TreeMapDemo {
    public static void main(String[] args) {
        // 1. 创建一个 TreeMap
        // key 使用 Integer，表示订单编号
        // value 使用 String，表示订单名称
        Map<Integer,String> map = new TreeMap<>();
        // 2. 放入 3 -> 订单3
        map.put(3,"订单3");
        // 3. 放入 1 -> 订单1
        map.put(1,"订单1");
        // 4. 放入 2 -> 订单2
        map.put(2,"订单2");
        // 5. 使用 entrySet 遍历 TreeMap
        // 观察输出顺序
        for(Map.Entry<Integer,String> m : map.entrySet()){
            System.out.println("key:"+m.getKey());
            System.out.println("value:"+m.getValue());
        }

        // 6. 思考：
        // 插入顺序是 3、1、2
        // 为什么输出顺序是 1、2、3？
        //TreeMap 会按照 key 自动排序。
        // 7. 总结：
        // TreeMap 会按照 key 自动排序
        // TreeMap 底层是红黑树
    }
}
