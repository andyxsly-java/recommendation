package teamproject.recommendation.executor;

import org.springframework.stereotype.Component;
import teamproject.recommendation.constants.TransactionTypeConstants;
import teamproject.recommendation.entity.RuleQuery;
import teamproject.recommendation.repository.RecommendationRepository;

import java.math.BigDecimal;
import java.util.UUID;

@Component
public class TransactionSumCompareDepositWithdrawExecutor implements QueryExecutor {

    private final RecommendationRepository recommendationRepository;

    public TransactionSumCompareDepositWithdrawExecutor(
            RecommendationRepository recommendationRepository) {
        this.recommendationRepository = recommendationRepository;
    }

    @Override
    public boolean supports(String query) {
        return "TRANSACTION_SUM_COMPARE_DEPOSIT_WITHDRAW".equals(query);
    }

    @Override
    public boolean execute(UUID userId, RuleQuery ruleQuery) {

        String productType = ruleQuery.getArguments().get(0);
        String operation = ruleQuery.getArguments().get(1);

        BigDecimal depositSum = recommendationRepository.getTransactionSum(
                userId,
                productType,
                TransactionTypeConstants.DEPOSIT
        );

        BigDecimal withdrawSum = recommendationRepository.getTransactionSum(
                userId,
                productType,
                TransactionTypeConstants.WITHDRAW
        );

        boolean result = compare(depositSum, operation, withdrawSum);

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
