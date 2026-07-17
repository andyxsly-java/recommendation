package teamproject.recommendation.service;

import org.springframework.stereotype.Service;
import teamproject.recommendation.dto.RecommendationDto;
import teamproject.recommendation.dto.RecommendationResponse;
import teamproject.recommendation.entity.RecommendationRuleEntity;
import teamproject.recommendation.entity.RuleQuery;
import teamproject.recommendation.executor.QueryExecutor;
import teamproject.recommendation.repository.DynamicRuleRepository;
import teamproject.recommendation.rules.RecommendationRuleSet;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class RecommendationService {

    private final List<RecommendationRuleSet> rules;
    private final DynamicRuleRepository dynamicRuleRepository;
    private final List<QueryExecutor> executors;

    public RecommendationService(List<RecommendationRuleSet> rules,
                                 DynamicRuleRepository dynamicRuleRepository,
                                 List<QueryExecutor> executors) {
        this.rules = rules;
        this.dynamicRuleRepository = dynamicRuleRepository;
        this.executors = executors;
    }

    public RecommendationResponse getRecommendation(UUID userId) {

        List<RecommendationDto> recommendations = new ArrayList<>();

        recommendations.addAll(
                rules.stream()
                        .map(rule -> rule.check(userId))
                        .flatMap(Optional::stream)
                        .toList()
        );

        List<RecommendationRuleEntity> dynamicRules = dynamicRuleRepository.findAll();

        for (RecommendationRuleEntity dynamicRule : dynamicRules) {

            boolean passed = true;

            for (RuleQuery query : dynamicRule.getRule()) {

                QueryExecutor executor = findExecutor(query.getQuery());

                if (!executor.execute(userId, query)) {
                    passed = false;
                    break;
                }
            }

            if (passed) {
                recommendations.add(
                        new RecommendationDto(
                                dynamicRule.getProductId(),
                                dynamicRule.getProductName(),
                                dynamicRule.getProductText()
                        )
                );
            }
        }

        return new RecommendationResponse(userId, recommendations);
    }

    private QueryExecutor findExecutor(String query) {

        for (QueryExecutor executor : executors) {
            if (executor.supports(query)) {
                return executor;
            }
        }

        throw new IllegalArgumentException("Unsupported query type: " + query);
    }
}