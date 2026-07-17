package teamproject.recommendation.executor;

import org.springframework.stereotype.Component;
import teamproject.recommendation.entity.RuleQuery;
import teamproject.recommendation.repository.RecommendationRepository;

import java.math.BigDecimal;
import java.util.UUID;

@Component
public class TransactionSumCompareExecutor implements QueryExecutor {

    private final RecommendationRepository recommendationRepository;

    public TransactionSumCompareExecutor(RecommendationRepository recommendationRepository) {
        this.recommendationRepository = recommendationRepository;
    }

    @Override
    public boolean supports(String query) {
        return "TRANSACTION_SUM_COMPARE".equals(query);
    }

    @Override
    public boolean execute(UUID userId, RuleQuery ruleQuery) {

        String productType = ruleQuery.getArguments().get(0);
        String transactionType = ruleQuery.getArguments().get(1);
        String operation = ruleQuery.getArguments().get(2);
        BigDecimal value = new BigDecimal(ruleQuery.getArguments().get(3));

        BigDecimal transactionSum = recommendationRepository.getTransactionSum(
                userId,
                productType, transactionType
        );

        boolean result = compare(transactionSum, operation, value);

        return ruleQuery.isNegate() ? !result : result;
    }

    private boolean compare(BigDecimal left,
                            String operation,
                            BigDecimal right) {

        return switch (operation) {
            case ">" -> left.compareTo(right) > 0;
            case "<" -> left.compareTo(right) < 0;
            case "=" -> left.compareTo(right) == 0;
            case ">=" -> left.compareTo(right) >= 0;
            case "<=" -> left.compareTo(right) <= 0;
            default -> throw new IllegalArgumentException(
                    "Unknown compare operation: " + operation
            );
        };
    }
}