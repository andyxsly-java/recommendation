package teamproject.recommendation.configuration;

import liquibase.integration.spring.SpringLiquibase;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import teamproject.recommendation.repository.DynamicRuleRepository;
import teamproject.recommendation.repository.RuleStatisticRepository;

import javax.sql.DataSource;
import java.util.HashMap;

@Configuration

@EnableJpaRepositories(
        basePackageClasses = {
                DynamicRuleRepository.class,
                RuleStatisticRepository.class
        }
)

@EntityScan(
        basePackages = "teamproject.recommendation.entity"
)
public class RecommendationJpaConfiguration {

    @Bean
    @ConfigurationProperties("recommendation.datasource")
    public DataSourceProperties recommendationDataSourceProperties() {
        return new DataSourceProperties();
    }

    @Bean
    public DataSource recommendationDataSource(
            @Qualifier("recommendationDataSourceProperties")
            DataSourceProperties properties) {

        return properties.initializeDataSourceBuilder().build();
    }

    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory(
            @Qualifier("recommendationDataSource")
            DataSource dataSource) {

        LocalContainerEntityManagerFactoryBean factory =
                new LocalContainerEntityManagerFactoryBean();

        factory.setDataSource(dataSource);

        factory.setPackagesToScan("teamproject.recommendation.entity");

        factory.setJpaVendorAdapter(new HibernateJpaVendorAdapter());

        HashMap<String, Object> properties = new HashMap<>();

        properties.put("hibernate.hbm2ddl.auto", "none");

        factory.setJpaPropertyMap(properties);

        return factory;
    }

    @Bean
    public JpaTransactionManager transactionManager(
            @Qualifier("entityManagerFactory")
            LocalContainerEntityManagerFactoryBean factory) {

        return new JpaTransactionManager(factory.getObject());
    }

    @Bean
    public SpringLiquibase liquibase(
            @Qualifier("recommendationDataSource")
            DataSource dataSource) {

        SpringLiquibase liquibase = new SpringLiquibase();

        liquibase.setDataSource(dataSource);

        liquibase.setChangeLog("classpath:/db/changelog-master.yaml");

        return liquibase;
    }
}