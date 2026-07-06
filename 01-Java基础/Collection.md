##Collection
Collection是Java集合体系中的根接口之一，是一组对象，实现了Iterable接口，通过Iterator实现遍历循环。List和Set都继承了Collection。
List表示有序、有下标、可重复的集合，其中子类有：
ArrayList：底层是数组，查询快，末尾追加数据快，中间插入数据慢
LinkedList：底层是链表，查询慢，中间插入数据快
Set表示有序、不可重复集合，其中子类有：
HashSet：底层有HashMap实现，保证元素不重复
LinkedHashSet：在保证不重复的基础上，实现有序
TreeSet：基于红黑树结构实现，保证排序

## Map
Map 不继承 Collection，它是单独的键值对结构。
Map 存储 key-value 映射关系，key 不允许重复，value 可以重复。
常见实现：
- HashMap：最常用，key 无序，允许一个 null key
- LinkedHashMap：在 HashMap 基础上维护插入顺序
- TreeMap：按照 key 排序，底层是红黑树
- Hashtable：早期线程安全 Map，不推荐新项目使用
- ConcurrentHashMap：并发场景下使用的线程安全 Map

## 总结
List 管顺序和下标，允许重复。
Set 管唯一，不允许重复。
Map 管映射，存储 key-value，不属于 Collection。
Iterable 决定集合能否被 for-each 遍历，Collection 继承了 Iterable。