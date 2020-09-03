package ua.ihor.blablatracker.service.external;

import org.junit.jupiter.api.Test;

import java.util.concurrent.TimeUnit;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

public class UserRatingCacheTest {
    private static final int DEFAULT_EXPIRY_PERIOD_SEC = 300;

    @Test
    public void testCache() {
        UserRatingCache cache = new UserRatingCache(3, DEFAULT_EXPIRY_PERIOD_SEC);
        cache.put(1, 2);
        cache.put(2, 3);
        cache.put(3, 4);

        assertEquals(cache.get(1).intValue(), 2);
        assertEquals(cache.get(2).intValue(), 3);
        assertEquals(cache.get(3).intValue(), 4);
    }

    @Test
    public void testCacheRewrite() {
        UserRatingCache cache = new UserRatingCache(2, DEFAULT_EXPIRY_PERIOD_SEC);
        cache.put(1, 2);
        cache.put(2, 3);
        cache.put(3, 4);

        assertNull(cache.get(1));
        assertEquals(cache.get(2).intValue(), 3);
        assertEquals(cache.get(3).intValue(), 4);
    }

    @Test
    public void testCacheExpiry() throws InterruptedException {
        UserRatingCache cache = new UserRatingCache(2, 2);
        cache.put(1, 2);
        TimeUnit.MILLISECONDS.sleep(1100);
        cache.put(2, 3);
        TimeUnit.MILLISECONDS.sleep(1100);

        assertNull(cache.get(1));
        assertEquals(cache.get(2).intValue(), 3);
    }
}
