package org.service.output_port;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class LruIdCache<T, E> {

    public class Node {
        E val;
        T key;
        Node next;
        Node prev;

        public Node(T key, E val) {
            this.val = val;
            this.key = key;
        }
    }

    private Node head;
    private Node tail;
    private int capacity;
    private Map<T, Node> cache;

    public LruIdCache(int capacity) {
        this.capacity = capacity;
        head = new Node(null, null);
        tail = new Node(null, null);
        head.next = tail;
        tail.prev = head;
        cache = new HashMap<>();
    }

    private void addNode(Node node) {
        Node nextNode = head.next;
        head.next = node;
        node.prev = head;

        node.next = nextNode;
        nextNode.prev = node;
    }

    private void deleteNode(Node node) {
        Node prevNode = node.prev;
        Node tail = node.next;

        tail.prev = prevNode;
        prevNode.next = tail;
    }

    public E get(T key) {
        if (cache.containsKey(key)) {
            Node node = cache.get(key);
            deleteNode(node);
            addNode(node);
            return node.val;
        }
        return null;
    }

    public boolean containsKey(T key) {
        if (cache.containsKey(key)) {
            Node node = cache.get(key);
            deleteNode(node);
            addNode(node);
            return true;
        } else {
            return false;
        }
    }

    public void put(T key, E value) {
        if (cache.containsKey(key)) {
            deleteNode(cache.get(key));
        }else if(capacity == cache.size()) {
            T delKey = tail.prev.key;
            deleteNode(cache.get(delKey));
            cache.remove(delKey);
        }
        Node newNode = new Node(key, value);
        addNode(newNode);
        cache.put(key, newNode);
    }

    public void remove(T key) {
        if (cache.containsKey(key)) {
            deleteNode(cache.get(key));
            cache.remove(key);
        }
    }
}
