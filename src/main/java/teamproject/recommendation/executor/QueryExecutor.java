package teamproject.recommendation.executor;

import teamproject.recommendation.entity.RuleQuery;

import java.util.UUID;

public interface QueryExecutor {

    boolean supports(String query);

    boolean execute(UUID userId, RuleQuery ruleQuery);
}
