package com.bupt.LRUCache;

import java.util.HashMap;
import java.util.Map;

/*
* 基于hashmap实现
* */
public class LRUMap2<K,V> implements LRU<K,V>{
    private final int maxSize; //缓存容量
    private Node head;
    private Node tail;

    private HashMap<K, Node<K,V>> map;

    public LRUMap2(int maxSize){
        this.maxSize=maxSize;
        map=new HashMap<>();
    }
    //返回，同时访问到的元素移到链表开头
    @Override
    public synchronized V get(Object key) {
        Node<K,V> node=map.get(key);
        if (node==null) return null;
        moveToHead(node);
        return node.value;
    }

    //添加元素
    @Override
    public synchronized void put(K k, V v) {
        Node<K,V> node=null;
        if (!containsKey(k)){
            if (map.size()>=maxSize){
                removeTail();
            }
            node=new Node<>(k,v);
        }else {
            node=map.get(k);
            node.value=v;
        }
        moveToHead(node);
        map.put(k,node);
    }
    public synchronized boolean containsKey(K key){
        if (key==null) return false;
        return map.containsKey(key);
    }
    //最后访问的放在链表头
    private synchronized void  moveToHead(Node node){
        if (node==head) return;
        //删除当前节点的前后连接
        if (node.pre!=null) node.pre.next=node.next; //前驱节点的next指向下一节点
        if (node.next!=null) node.next.pre=node.pre; //后继节点的pre指向前一个节点
        if (node==tail) tail=tail.pre;
        //cache初始加入第一个元素
        if (head==null||tail==null){
            head=node;
            tail=node;
            return;
        }
        //节点移到开头
        node.next=head;
        head.pre=node;
        head=node;
        head.pre=null;
    }
    //当超出容量后，删除尾部最久未访问的节点
    private synchronized void removeTail(){
        if (tail!=null){
            Node node=tail;
            tail=tail.pre;
            if (tail!=null) tail.next=null;
            //只有一个节点
            else head=null;
            //删除节点
            map.remove(node.key);
        }
    }
    public synchronized void clear(){
        head=null;
        tail=null;
        map.clear();
    }
    public synchronized void remove(K key){
        Node node=map.get(key);
        if (node!=null){
            //删除链表上的node
            if (node.pre!=null) node.pre.next=node.next;
            if (node.next!=null) node.next.pre=node.pre;
            if (head==node) head=node.next;
            if (tail==node) tail=node.pre;
            node=null; //gc help
        }
        //删除实际的数据
        map.remove(key);
    }
    public synchronized Node<K,V> getNode(K key){
        return map.get(key);
    }
    class Node<K,V>{
        public Node pre;
        public Node next;
        public K key;
        public V value;
        public Node(){}
        public Node(K key,V value){
            this.value=value;
            this.key=key;
        }
    }
}
