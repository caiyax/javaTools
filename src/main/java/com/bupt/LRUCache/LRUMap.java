package com.bupt.LRUCache;

import java.util.LinkedHashMap;
import java.util.Map;
/*
* 基于LinkedHashMap实现LRU
* LinkedHashMap提供了按照访问顺序存储的功能，
* 需要做的就是在元素大于容量的时候删除最久未用的元素即可
* */
public class LRUMap<K,V> extends LinkedHashMap<K,V> implements Map<K,V>{
    private final int maxSize; //缓存的容量大小

    public LRUMap(int maxSize){
        //accessOrder为true是，访问顺序会变成按照访问顺序存储
        super((int) Math.ceil((1f*maxSize)/0.75f)+16,0.75f,true);
        this.maxSize=maxSize;
    }

    @Override
    protected synchronized boolean removeEldestEntry(Map.Entry<K,V> eldest){
        return size()>maxSize;
    }

    @Override
    public synchronized int size(){
        return super.size();
    }

    @Override
    public boolean isEmpty() {
        return super.isEmpty();
    }

    @Override
    public synchronized V get(Object key){
        return super.get(key);
    }

    @Override
    public V remove(Object key) {
        return super.remove(key);
    }

    @Override
    public void clear() {
        super.clear();
    }
}
