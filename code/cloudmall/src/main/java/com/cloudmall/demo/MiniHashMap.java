package com.cloudmall.demo;

public class MiniHashMap {
    private Node[] table;
    private int size;
    private int threshold;
    private final float loadFactor = 0.75f;

    public MiniHashMap() {
        size = 0;
        table = new Node[16];
        threshold = (int) (16 * loadFactor);
    }

    public void put(String key, String value) {
        // 1. 计算 hash
        int hash = key.hashCode();
        // 2. 定位数组下标
        int index = hash & (table.length - 1);
        // 3. 如果桶为空，直接放
        if (table[index] == null) {
            table[index] = new Node(key, value, hash, null);
            size++;
        }
        // 4. 如果桶不为空，遍历链表
        if (table[index] != null) {
            Node e = table[index];
            for(int i=0; ;++i){
                if(e.next == null){
                    // 6. key 不同则追加到链表尾部
                    e.next = new Node(key, value, hash, null);
                    size++;
                    break;
                } else {
                    // 5. key 相同则覆盖
                    if(e.key == key){
                        e.value = value;
                        break;
                    }
                }
                e = e.next;
            }
        }
        // 7. size 超过 threshold 就 resize
        if(size > threshold){
            resize();
        }
    }

    public String get(String key) {
        // 1. 计算 hash
        int hash = key.hashCode();
        // 2. 定位数组下标
        int index = hash & (table.length - 1);
        // 3. 遍历链表
        Node e = table[index];
        if(e == null){
            return "找不到value";
        } else {
            for(int i=0; ;++i){
                if(e.next == null){
                    return "找不到value";
                } else {
                    if(e.key.equals(key)){
                        // 4. 找到 key 返回 value
                        return e.value;
                    }
                }
                e = e.next;
            }
        }
    }

    public int size(){
        return size;
    }

    private void resize() {
        int newLength = table.length << 1;
        // 1. 创建 2 倍长度的新数组
        Node[] newTable = new Node[table.length << 1];
        // 2. 遍历旧数组
        for(int i = 0; i< table.length;i++){
            // 3. 把旧节点重新放到新数组
            newTable[i] = table[i];
        }
        table = newTable;
        // 4. 更新 threshold
        threshold = (int) (newLength * loadFactor);
    }

    static class Node {
        String key;
        String value;
        int hash;
        Node next;

        Node(String key, String value, int hash, Node next) {
            this.key = key;
            this.value = value;
            this.hash = hash;
            this.next = next;
        }
    }

    public static void main(String[] args) {
        MiniHashMap map = new MiniHashMap();

        map.put("name", "CloudMall");
        map.put("age", "1");
        map.put("name", "CloudMall-V2");

        System.out.println(map.get("name")); // CloudMall-V2
        System.out.println(map.get("age"));  // 1
        System.out.println(map.get("none")); // null

        for (int i = 0; i < 100; i++) {
            map.put("key" + i, "value" + i);
        }
        System.out.println(map.get("key99")); // value99
    }
}
