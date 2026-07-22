package teamproject.recommendation.executor;

import org.springframework.stereotype.Component;
import teamproject.recommendation.entity.RuleQuery;
import teamproject.recommendation.repository.RecommendationRepository;

import java.util.UUID;

@Component
public class UserOfExecutor implements QueryExecutor {

    private final RecommendationRepository recommendationRepository;

    public UserOfExecutor(RecommendationRepository recommendationRepository) {
        this.recommendationRepository = recommendationRepository;
    }

    @Override
    public boolean supports(String query) {
        return "USER_OF".equals(query);
    }

    @Override
    public boolean execute(UUID userId, RuleQuery ruleQuery) {

        String productType = ruleQuery.getArguments().get(0);

        boolean result = recommendationRepository.isUserOfProductType(
                userId,
                productType
        );

        return ruleQuery.isNegate() ? !result : result;
    }
}
