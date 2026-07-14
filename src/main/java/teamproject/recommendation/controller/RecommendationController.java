package teamproject.recommendation.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import teamproject.recommendation.dto.RecommendationResponse;
import teamproject.recommendation.service.RecommendationService;

import java.util.UUID;

@RestController
@RequestMapping("/recommendation")
public class RecommendationController {

    private final RecommendationService recommendationService;

    public RecommendationController(RecommendationService recommendationService) {
        this.recommendationService = recommendationService;
    }

    @GetMapping("/{userId}")
    public teamproject.recommendation.dto.RecommendationResponse getRecommendation(
            @PathVariable("user_id") UUID userId) {

        return recommendationService.getRecommendation(userId);

    }
}
