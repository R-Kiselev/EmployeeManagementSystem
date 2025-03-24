package com.example.employeemanagementsystem.utils;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import lombok.Getter;
import org.springframework.stereotype.Component;

@SuppressWarnings("squid:S6829")
@Component
public class InMemoryCache<K, V> {

    private static class CacheEntry<V> {
        @Getter
        private final V value;
        private final long expiryTime;

        public CacheEntry(V value, long expiryTime) {
            this.value = value;
            this.expiryTime = expiryTime;
        }

        public boolean isExpired() {
            return System.currentTimeMillis() >= expiryTime;
        }
    }

    private final Map<K, CacheEntry<V>> cache = new ConcurrentHashMap<>();
    private final long ttlMillis;

    public InMemoryCache() {
        this(300_000); // Значение по умолчанию: 5 минут
    }

    public InMemoryCache(long ttlMillis) {
        this.ttlMillis = ttlMillis;
        ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor();
        scheduler.scheduleAtFixedRate(this::evictExpiredEntries,
            ttlMillis, ttlMillis, TimeUnit.MILLISECONDS);
    }

    public V get(K key) {
        CacheEntry<V> entry = cache.get(key);
        if (entry == null) {
            return null;
        }
        if (entry.isExpired()) {
            cache.remove(key);
            return null;
        }
        return entry.getValue();
    }

    public void put(K key, V value) {
        CacheEntry<V> entry = new CacheEntry<>(value, System.currentTimeMillis() + ttlMillis);
        cache.put(key, entry);
    }

    public void evict(K key) {
        cache.remove(key);
    }

    public void clear() {
        cache.clear();
    }

    private void evictExpiredEntries() {
        for (Map.Entry<K, CacheEntry<V>> entry : cache.entrySet()) {
            if (entry.getValue().isExpired()) {
                cache.remove(entry.getKey());
            }
        }
    }
}