package teamproject.recommendation.repository;

import org.springframework.jdbc.core.JdbcTemplate;

import java.math.BigDecimal;
import java.util.UUID;

public class RecommendationRepository {

    private final JdbcTemplate jdbcTemplate;

    public RecommendationRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public boolean hasProduct(UUID userId, String productType) {

        String sql = """
            SELECT COUNT(*) > 0
            FROM transactions t
            JOIN products p
                 ON t.product_id = p.id
            WHERE t.user_id = ?
              AND p.type = ?
            """;

        Boolean result = jdbcTemplate.queryForObject(
                sql,
                Boolean.class,
                userId,
                productType);

        return Boolean.TRUE.equals(result);
    }

    public BigDecimal getDepositSum(UUID userId,
                                    String productType) {

        String sql = """
                SELECT COALESCE(SUM(t.amount),0)
                FROM transactions t
                JOIN products p
                    ON t.product_id = p.id
                WHERE t.user_id = ?
                  AND p.type = ?
                  AND t.type = 'DEPOSIT'
                """;

        return jdbcTemplate.queryForObject(
                sql,
                BigDecimal.class,
                userId,
                productType
        );
    }

    public BigDecimal getWithdrawSum(UUID userId, String productType) {

        String sql = """
                SELECT COALESCE(SUM(t.amount), 0)
                FROM transactions t
                JOIN products p
                ON t.product_id = p.id
                             WHERE t.user_id = ?
                             AND p.type = ?
                             AND t.type = 'WITHDRAW'
                """;
        return jdbcTemplate.queryForObject(sql, BigDecimal.class, userId, productType);
    }
}
