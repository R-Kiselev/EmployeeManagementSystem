package com.example.employeemanagementsystem.utils;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.LinkedBlockingDeque;
import lombok.Getter;
import org.springframework.stereotype.Component;

@SuppressWarnings("squid:S6829")
@Component
public class InMemoryCache<K, V> {

    private static class CacheEntry<V> {
        @Getter
        private final V value;

        public CacheEntry(V value) {
            this.value = value;
        }
    }

    private final Map<K, CacheEntry<V>> cache = new ConcurrentHashMap<>();
    private final LinkedBlockingDeque<K> queue = new LinkedBlockingDeque<>();
    private final int capacity;

    public InMemoryCache() {
        this(128);
    }

    public InMemoryCache(int capacity) {
        if (capacity <= 0) {
            throw new IllegalArgumentException("Capacity must be positive");
        }
        this.capacity = capacity;
    }

    public V get(K key) {
        CacheEntry<V> entry = cache.get(key);
        if (entry == null) {
            return null;
        }

        queue.remove(key);
        queue.offer(key);

        return entry.getValue();
    }

    public void put(K key, V value) {
        CacheEntry<V> entry = new CacheEntry<>(value);

        if (cache.containsKey(key)) {

            cache.put(key, entry);
            queue.remove(key);
            queue.offer(key);
            return;
        }

        if (cache.size() >= capacity) {

            K lruKey = queue.poll();
            if (lruKey != null) {
                cache.remove(lruKey);
            }
        }

        cache.put(key, entry);
        queue.offer(key);
    }

    public void evict(K key) {
        cache.remove(key);
        queue.remove(key);
    }

    public void clear() {
        cache.clear();
        queue.clear();
    }
}