package teamproject.recommendation.service;

import org.springframework.stereotype.Service;
import teamproject.recommendation.dto.RecommendationDto;
import teamproject.recommendation.dto.RecommendationResponse;
import teamproject.recommendation.rules.RecommendationRuleSet;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class RecommendationService {

    private final List<RecommendationRuleSet> rules;

    public RecommendationService(List<RecommendationRuleSet> rules) {
        this.rules = rules;
    }

    public RecommendationResponse getRecommendation(UUID userId) {
        List<RecommendationDto> recommendations =
                rules.stream()
                        .map(rule -> rule.check(userId))
                        .flatMap(Optional::stream)
                        .toList();

        return new RecommendationResponse(userId, recommendations);

    }
}
