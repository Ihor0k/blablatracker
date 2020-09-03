package ua.ihor.blablatracker.service.external;

import org.springframework.beans.factory.annotation.Value;

import javax.annotation.PostConstruct;
import java.io.IOException;

public abstract class AbstractExternalUserRatingService implements UserRatingService{
    public static final int DEFAULT_USER_RATING = 1;

    @Value("${user.rating.cache.size}")
    private int cacheSize;
    @Value("${user.rating.cache.expiry-period-sec}")
    private long expiryPeriodSec;

    protected UserRatingCache userRatingCache;

    @PostConstruct
    private void init() {
        this.userRatingCache = new UserRatingCache(cacheSize, expiryPeriodSec);
    }

    @Override
    public int getUserRating(long userId) {
        try {
            Integer rating = userRatingCache.get(userId);
            if (rating == null) {
                rating = makeRequest();
                userRatingCache.put(userId, rating);
            }
            return rating;
        } catch (IOException e) {
            return DEFAULT_USER_RATING;
        }
    }

    protected abstract int makeRequest() throws IOException;
}
