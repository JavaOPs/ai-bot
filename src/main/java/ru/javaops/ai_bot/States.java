package ru.javaops.ai_bot;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;

import java.util.concurrent.TimeUnit;

public class States<S> {
    private final Cache<Long, S> cache = Caffeine.newBuilder()
            .expireAfterWrite(120, TimeUnit.MINUTES)
            .maximumSize(30000)
            .build();

    public S getCurrent(long tgId) {
        return cache.getIfPresent(tgId);
    }

    public S update(long tgId, S state) {
        cache.put(tgId, state);
        return state;
    }

    public void invalidate(long tgId) {
        cache.invalidate(tgId);
    }
}