package teamproject.recommendation.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import teamproject.recommendation.entity.RuleStatisticEntity;

import java.util.UUID;

public interface RuleStatisticRepository
        extends JpaRepository<RuleStatisticEntity, UUID> {
}
