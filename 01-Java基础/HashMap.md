## resize 源码解读
final Node<K,V>[] resize() {
	//table是当前的node对象，oldTab指向node对象
	Node<K,V>[] oldTab = table;
	//获取oldTab的长度，null则0
	int oldCap = (oldTab == null) ? 0 : oldTab.length;
	int oldThr = threshold;
	int newCap, newThr = 0;
	if (oldCap > 0) {
		//当oldCap达到扩容的阈值
		if (oldCap >= MAXIMUM_CAPACITY) {
			//直接赋值threshold最大值
			threshold = Integer.MAX_VALUE;
			//返回旧对象
			return oldTab;
		}6
		//当oldCap做位移运算后大于hashmap的初始化最大值且小于扩容阈值
		else if ((newCap = oldCap << 1) < MAXIMUM_CAPACITY &&
				 oldCap >= DEFAULT_INITIAL_CAPACITY)
			//做翻倍扩容
			newThr = oldThr << 1; // double threshold
	}
	else if (oldThr > 0) // initial capacity was placed in threshold
		newCap = oldThr;
	else {               // zero initial threshold signifies using defaults
		newCap = DEFAULT_INITIAL_CAPACITY;
		newThr = (int)(DEFAULT_LOAD_FACTOR * DEFAULT_INITIAL_CAPACITY);
	}
	if (newThr == 0) {
		//等于0时则按hashmap的初始化赋值
		float ft = (float)newCap * loadFactor;
		newThr = (newCap < MAXIMUM_CAPACITY && ft < (float)MAXIMUM_CAPACITY ?
				  (int)ft : Integer.MAX_VALUE);
	}
	threshold = newThr;
	@SuppressWarnings({"rawtypes","unchecked"})
	Node<K,V>[] newTab = (Node<K,V>[])new Node[newCap];
	table = newTab;
	if (oldTab != null) {
		for (int j = 0; j < oldCap; ++j) {
			Node<K,V> e;
			if ((e = oldTab[j]) != null) {
				oldTab[j] = null;
				if (e.next == null)
					//没有下一节点，说明是头结点，直接赋值
					newTab[e.hash & (newCap - 1)] = e;
				else if (e instanceof TreeNode)
					//这里是红黑树的处理
					((TreeNode<K,V>)e).split(this, newTab, j, oldCap);
				else { // preserve order
					//这里是链表的处理
					Node<K,V> loHead = null, loTail = null;
					Node<K,V> hiHead = null, hiTail = null;
					Node<K,V> next;
					do {
						next = e.next;
						if ((e.hash & oldCap) == 0) {
							if (loTail == null)
								loHead = e;
							else
								loTail.next = e;
							loTail = e;
						}
						else {
							if (hiTail == null)
								hiHead = e;
							else
								hiTail.next = e;
							hiTail = e;
						}
					} while ((e = next) != null);
					if (loTail != null) {
						loTail.next = null;
						newTab[j] = loHead;
					}
					if (hiTail != null) {
						hiTail.next = null;
						newTab[j + oldCap] = hiHead;
					}
				}
			}
		}
	}
	return newTab;
}

## resize 流程理解
capacity代表hashmap的长度，threshold代表hashmap的扩容阈值（threshold = capacity * loadFactor），hashmap初始化默认长度是16，当capacity大于threshold则触发扩容，扩容是做2倍扩容，扩容不是把每个节点都重新hash一遍，根据(e.hash & oldCap) == 0判断，等于0则保持原来的位置，不等于0则移到原来坐标+oldCap的位置。

## putVal 源码解读
final V putVal(int hash, K key, V value, boolean onlyIfAbsent,boolean evict) {
	Node<K,V>[] tab; Node<K,V> p; int n, i;
	if ((tab = table) == null || (n = tab.length) == 0)
		//初始化hashmap长度
		n = (tab = resize()).length;
	if ((p = tab[i = (n - 1) & hash]) == null)
		//如果node数组的下标取不到node，说明没有冲突，直接赋值
		tab[i] = newNode(hash, key, value, null);
	else {
		Node<K,V> e; K k;
		if (p.hash == hash &&
			((k = p.key) == key || (key != null && key.equals(k))))
			//key已经存在了，先记录旧节点
			e = p;
		else if (p instanceof TreeNode)
		//红黑树处理
			e = ((TreeNode<K,V>)p).putTreeVal(this, tab, hash, key, value);
		else {
			//链表处理
			for (int binCount = 0; ; ++binCount) {
				if ((e = p.next) == null) {
					//末尾节点，直接赋值
					p.next = newNode(hash, key, value, null);
					if (binCount >= TREEIFY_THRESHOLD - 1) // -1 for 1st
						//链表长度达到 8 只是触发 treeifyBin，但是否真正转红黑树，还要看 table 长度是否达到 64。
						treeifyBin(tab, hash);
					break;
				}
				if (e.hash == hash &&
					((k = e.key) == key || (key != null && key.equals(k))))
					break;
				//链表其他节点保持不变
				p = e;
			}
		}
		if (e != null) { // existing mapping for key
			V oldValue = e.value;
			if (!onlyIfAbsent || oldValue == null)
				e.value = value;
			afterNodeAccess(e);
			return oldValue;
		}
	}
	++modCount;
	if (++size > threshold)
		resize();
	afterNodeInsertion(evict);
	return null;
}

## putVal 流程理解
put方法的处理分：
1、当key已存在时，先记录旧节点，再判断value是否相同，不相同则做覆盖
2、key不存在时：
（1）根据hash(key)作为下标定位数组，如果没有节点，直接赋值；
（2）如果有节点，就是hash冲突了，按照链表的形式存放，如果链表的长度>8且小于64，则只做扩容处理，大于64则转变成红黑树。

## hash 方法源码解读
static final int hash(Object key) {
	int h;
	//hashcode先做位移运算，再做异或
	return (key == null) ? 0 : (h = key.hashCode()) ^ (h >>> 16);
}

## hash 方法理解
减少hash冲突