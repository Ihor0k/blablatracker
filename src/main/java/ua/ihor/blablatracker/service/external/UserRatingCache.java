package ua.ihor.blablatracker.service.external;

import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;

class UserRatingCache {
    private final int cacheSize;
    private final long expiryPeriodMs;

    private final HashMap<Long, Entry> cache;

    public UserRatingCache(int cacheSize, long expiryPeriodSec) {
        this.cacheSize = cacheSize;
        this.expiryPeriodMs = expiryPeriodSec * 1000;
        this.cache = new LinkedHashMap<>(cacheSize);
    }

    public Integer get(long userId) {
        Entry entry = cache.get(userId);
        return entry != null && entry.isValid() ? entry.rating : null;
    }

    public void put(long userId, int rating) {
        if (cache.size() >= cacheSize) {
            Iterator<Map.Entry<Long, Entry>> iterator = cache.entrySet().iterator();
            if (iterator.hasNext()) {
                iterator.next();
                iterator.remove();
            }
            while (iterator.hasNext()) {
                if (!iterator.next().getValue().isValid()) {
                    iterator.remove();
                } else {
                    break;
                }
            }
        }
        cache.put(userId, new Entry(rating));
    }

    private class Entry {
        final int rating;
        final long expiresAt;

        Entry(int rating) {
            this.rating = rating;
            this.expiresAt = System.currentTimeMillis() + expiryPeriodMs;
        }

        boolean isValid() {
            return expiresAt > System.currentTimeMillis();
        }
    }
}
