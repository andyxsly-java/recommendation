package teamproject.recommendation.executor;

import teamproject.recommendation.entity.RuleQuery;

import java.util.UUID;

/**
 * Исполнитель запроса динамического правила.
 *
 * Позволяет обрабатывать различные типы условий,
 * сохраненных в JSON-описании правила.
 */
public interface QueryExecutor {

    /**
     * Поддерживает ли данный тип запроса.
     */
    boolean supports(String query);

    /**
     * Выполняет проверку условия.
     */
    boolean execute(UUID userId, RuleQuery ruleQuery);
}
