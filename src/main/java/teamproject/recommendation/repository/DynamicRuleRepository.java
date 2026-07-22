package teamproject.recommendation.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import teamproject.recommendation.entity.RecommendationRuleEntity;

import java.util.UUID;

public interface DynamicRuleRepository extends JpaRepository <RecommendationRuleEntity, UUID> {
}
