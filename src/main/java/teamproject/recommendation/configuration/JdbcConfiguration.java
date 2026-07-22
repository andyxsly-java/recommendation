package teamproject.recommendation.configuration;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;

@Configuration
public class JdbcConfiguration {

    @Bean
    public JdbcTemplate recommendationJdbcTemplate(
            @Qualifier("recommendationsDataSource")
            DataSource dataSource) {

        return new JdbcTemplate(dataSource);
    }
}
