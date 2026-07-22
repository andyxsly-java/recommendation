package teamproject.recommendation.repository;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.Duration;
import java.util.UUID;
/**
 * Репозиторий для выполнения запросов к транзакционной БД.
 *
 * Для уменьшения нагрузки на H2 используются
 * локальные кэши результатов запросов.
 */
@Repository
public class RecommendationRepository {

    private final JdbcTemplate jdbcTemplate;

    private final Cache<UserOfCacheKey, Boolean> userOfCache =
            Caffeine.newBuilder()
                    .expireAfterWrite(Duration.ofMinutes(10))
                    .maximumSize(10_000)
                    .build();

    private final Cache<ActiveUserCacheKey, Boolean> activeUserCache =
            Caffeine.newBuilder()
                    .expireAfterWrite(Duration.ofMinutes(10))
                    .maximumSize(10_000)
                    .build();

    private final Cache<TransactionSumCacheKey, BigDecimal> transactionSumCache =
            Caffeine.newBuilder()
                    .expireAfterWrite(Duration.ofMinutes(10))
                    .maximumSize(10_000)
                    .build();

    public RecommendationRepository(@Qualifier("recommendationJdbcTemplate") JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public boolean isUserOfProductType(UUID userId, String productType) {

        UserOfCacheKey key = new UserOfCacheKey(userId, productType);

        return userOfCache.get(key, k -> {

            String sql = """
                    SELECT EXISTS (
                        SELECT 1
                        FROM transactions t
                        JOIN products p
                            ON t.product_id = p.id
                        WHERE t.user_id = ?
                          AND p.type = ?
                    )
                    """;

            Boolean result = jdbcTemplate.queryForObject(
                    sql,
                    Boolean.class,
                    userId,
                    productType
            );

            return Boolean.TRUE.equals(result);
        });
    }

    public boolean isActiveUserOfProductType(UUID userId,
                                             String productType) {

        ActiveUserCacheKey key =
                new ActiveUserCacheKey(userId, productType);

        return activeUserCache.get(key, k -> {

            String sql = """
                    SELECT COUNT(*)
                    FROM transactions t
                    JOIN products p
                        ON t.product_id = p.id
                    WHERE t.user_id = ?
                      AND p.type = ?
                    """;

            Integer count = jdbcTemplate.queryForObject(
                    sql,
                    Integer.class,
                    userId,
                    productType
            );

            return count != null && count >= 5;
        });
    }

    public BigDecimal getTransactionSum(UUID userId,
                                        String productType,String transactionType) {

        TransactionSumCacheKey key =
                new TransactionSumCacheKey(
                        userId,
                        productType,
                        transactionType
                );

        return transactionSumCache.get(key, k -> {

            String sql = """
                    SELECT COALESCE(SUM(t.amount), 0)
                    FROM transactions t
                    JOIN products p
                        ON t.product_id = p.id
                    WHERE t.user_id = ?
                      AND p.type = ?
                      AND t.type = ?
                    """;

            BigDecimal result = jdbcTemplate.queryForObject(
                    sql,
                    BigDecimal.class,
                    userId,
                    productType,
                    transactionType
            );

            return result != null ? result : BigDecimal.ZERO;
        });
    }

    private record UserOfCacheKey(
            UUID userId,
            String productType
    ) {
    }

    private record ActiveUserCacheKey(
            UUID userId,
            String productType
    ) {
    }

    private record TransactionSumCacheKey(
            UUID userId,
            String productType,
            String transactionType
    ) {
    }

    public void clearCaches() {
        userOfCache.invalidateAll();
        activeUserCache.invalidateAll();
        transactionSumCache.invalidateAll();
    }
}
