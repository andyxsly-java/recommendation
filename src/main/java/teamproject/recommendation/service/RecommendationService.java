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

/**
 * Сервис, отвечающий за формирование рекомендаций для пользователей.
 *
 * <p>При формировании результата используются как статические правила,
 * реализованные в коде приложения, так и динамические правила,
 * сохраненные в базе данных.</p>
 */
@Service
public class RecommendationService {

    private final List<RecommendationRuleSet> rules;
    private final DynamicRuleRepository dynamicRuleRepository;
    private final List<QueryExecutor> executors;
    private final RuleStatisticService ruleStatisticService;

    public RecommendationService(List<RecommendationRuleSet> rules,
                                 DynamicRuleRepository dynamicRuleRepository,
                                 List<QueryExecutor> executors, RuleStatisticService ruleStatisticService) {
        this.rules = rules;
        this.dynamicRuleRepository = dynamicRuleRepository;
        this.executors = executors;
        this.ruleStatisticService = ruleStatisticService;
    }

    /**
     * Формирует список рекомендаций для указанного пользователя.
     *
     * <p>Сначала проверяются статические правила рекомендаций,
     * затем выполняются динамические правила. При успешном выполнении
     * динамического правила увеличивается счетчик его срабатываний.</p>
     *
     * @param userId идентификатор пользователя
     * @return список рекомендаций пользователя
     */
    public RecommendationResponse getRecommendation(UUID userId) {

        List<RecommendationDto> recommendations = new ArrayList<>();
        // Проверяем статические правила рекомендаций,
        // реализованные непосредственно в коде приложения.
        recommendations.addAll(
                rules.stream()
                        .map(rule -> rule.check(userId))
                        .flatMap(Optional::stream)
                        .toList()
        );
        // Проверяем динамические правила,
        // сохраненные в базе данных.
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
                // Если правило выполнилось,
                // увеличиваем счетчик его срабатываний.
                ruleStatisticService.increment(dynamicRule.getId());

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

    /**
     * Находит исполнителя для обработки указанного типа запроса.
     */
    private QueryExecutor findExecutor(String query) {

        for (QueryExecutor executor : executors) {
            if (executor.supports(query)) {
                return executor;
            }
        }

        throw new IllegalArgumentException("Unsupported query type: " + query);
    }
}