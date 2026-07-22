package teamproject.recommendation.rules;

import teamproject.recommendation.dto.RecommendationDto;

import java.util.Optional;
import java.util.UUID;

/**
 * Интерфейс статического правила рекомендации.
 */
public interface RecommendationRuleSet {

    /**
     * Проверяет выполнение правила для пользователя.
     *
     * @param userId идентификатор пользователя
     * @return рекомендация, если правило выполнилось
     */
    Optional<RecommendationDto> check(UUID userId);

}
