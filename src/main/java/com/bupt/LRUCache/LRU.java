package com.bupt.LRUCache;

public interface LRU<K,V> {
    public V get(Object key);
    public void put(K k,V v);
}
