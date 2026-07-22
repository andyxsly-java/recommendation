package teamproject.recommendation.executor;

import org.springframework.stereotype.Component;
import teamproject.recommendation.entity.RuleQuery;
import teamproject.recommendation.repository.RecommendationRepository;

import java.util.UUID;

@Component
public class ActiveUserOfExecutor implements QueryExecutor {

    private final RecommendationRepository recommendationRepository;

    public ActiveUserOfExecutor(RecommendationRepository recommendationRepository) {
        this.recommendationRepository = recommendationRepository;
    }

    @Override
    public boolean supports(String query) {
        return "ACTIVE_USER_OF".equals(query);
    }

    @Override
    public boolean execute(UUID userId, RuleQuery ruleQuery) {

        String productType = ruleQuery.getArguments().get(0);

        boolean result = recommendationRepository.isActiveUserOfProductType(
                userId,
                productType
        );

        return ruleQuery.isNegate() ? !result : result;
    }
}
