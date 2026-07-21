package teamproject.recommendation.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import teamproject.recommendation.entity.RuleStatisticEntity;

import java.util.UUID;

public interface RuleStatisticRepository
        extends JpaRepository<RuleStatisticEntity, UUID> {
    @Modifying
    @Query("""
            UPDATE RuleStatisticEntity rs
            SET rs.count = rs.count + 1
            WHERE rs.rule.id = :ruleId
            """)
    void increment(UUID ruleId);
}
