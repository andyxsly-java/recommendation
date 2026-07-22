package teamproject.recommendation.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import teamproject.recommendation.dto.RecommendationResponse;
import teamproject.recommendation.service.RecommendationService;

import java.util.UUID;

/**
 * REST-контроллер для получения рекомендаций пользователям.
 */

@Tag(
        name = "Recommendations",
        description = "Получение рекомендаций пользователям"
)
@RestController
@RequestMapping("/recommendation")
public class RecommendationController {

    private final RecommendationService recommendationService;

    public RecommendationController(RecommendationService recommendationService) {
        this.recommendationService = recommendationService;
    }

    /**
     * Возвращает список рекомендаций для указанного пользователя.
     *
     * @param userId идентификатор пользователя
     * @return рекомендации для пользователя
     */
    @Operation(
            summary = "Получить рекомендации",
            description = "Возвращает список рекомендаций для пользователя по его UUID."
    )
    @ApiResponse(
            responseCode = "200",
            description = "Рекомендации успешно получены",
            content = @Content(
                    mediaType = "application/json",
                    examples = @ExampleObject(
                            value = """
                                    {
                                      "user_id":"3fa85f64-5717-4562-b3fc-2c963f66afa6",
                                      "recommendations":[
                                        {
                                          "id":"59efc529-2fff-41af-baff-90ccd7402925",
                                          "name":"Top Saving",
                                          "text":"Описание продукта"
                                        }
                                      ]
                                    }
                                    """
                    )
            )
    )
    @GetMapping("/{user_id}")
    public RecommendationResponse getRecommendation(
            @PathVariable("user_id") UUID userId) {

        return recommendationService.getRecommendation(userId);
    }
}
