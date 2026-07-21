package teamproject.recommendation.repository;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public class UserRepository {

    private final JdbcTemplate jdbcTemplate;

    public UserRepository(
            @Qualifier("recommendationJdbcTemplate")
            JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<User> findByUsername(String username) {

        String sql = """
                SELECT id,
                       username,
                       first_name,
                       last_name
                FROM users
                WHERE username = ?
                """;

        return jdbcTemplate.query(
                sql,
                (rs, rowNum) -> new User(
                        UUID.fromString(rs.getString("id")),
                        rs.getString("username"),
                        rs.getString("first_name"),
                        rs.getString("last_name")
                ),
                username
        );
    }

    public record User(
            UUID id,
            String username,
            String firstName,
            String lastName
    ) {
    }
}
