package org.service.output_port.util;

import lombok.AllArgsConstructor;
import org.service.entity.BookingEntity;
import org.service.output_port.mapper.BookingMapper;
import org.service.output_port.model.Booking;
import org.springframework.cache.CacheManager;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
@AllArgsConstructor
public class CacheUtils {

    private final CacheManager cacheManager;

    public void revoke(String cacheName, String key) {
        Optional.ofNullable(cacheManager
                        .getCache(cacheName))
                .ifPresent(cache -> cache.evictIfPresent(key));
    }

    public void createBooking(String cacheName, String key, BookingEntity value) {
        Optional.ofNullable(cacheManager.getCache(cacheName))
                .ifPresent(
                        cache -> Optional.ofNullable(cache.get(key, List.class))
                                .map(e -> e.add(value))
                );
    }
}
