package teamproject.recommendation.rules;

import org.springframework.stereotype.Component;
import teamproject.recommendation.dto.RecommendationDto;
import teamproject.recommendation.repository.RecommendationRepository;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.UUID;

@Component
public class TopSavingRule implements RecommendationRuleSet {

    private static final UUID PRODUCT_ID =
            UUID.fromString("59efc529-2fff-41af-baff-90ccd7402925");

    private static final String PRODUCT_NAME = "Top Saving";

    private static final String PRODUCT_TEXT =
            """
                    Откройте свою собственную «Копилку» с нашим банком!
                    «Копилка» — это уникальный банковский инструмент, который поможет вам легко и удобно накапливать деньги на важные цели.
                    Больше никаких забытых чеков и потерянных квитанций — всё под контролем!
                    Преимущества «Копилки»:
                    Накопление средств на конкретные цели. Установите лимит и срок накопления, и банк будет автоматически переводить определенную сумму на ваш счет.
                    Прозрачность и контроль. Отслеживайте свои доходы и расходы, контролируйте процесс накопления и корректируйте стратегию при необходимости.
                    Безопасность и надежность. Ваши средства находятся под защитой банка, а доступ к ним возможен только через мобильное приложение или интернет-банкинг.
                    Начните использовать «Копилку» уже сегодня и станьте ближе к своим финансовым целям!
            """;

    private final RecommendationRepository repository;

    public TopSavingRule(RecommendationRepository repository) {
        this.repository = repository;

    }

    @Override
    public Optional<RecommendationDto> check(UUID userId) {

        boolean hasDebit = repository.hasProduct(userId, "DEBIT");

        BigDecimal debitDeposit =
                repository.getDepositSum(userId, "DEBIT");

        BigDecimal savingDeposit =
                repository.getDepositSum(userId, "SAVING");

        BigDecimal debitWithdraw =
                repository.getWithdrawSum(userId, "DEBIT");

        boolean enoughMoney =
                debitDeposit.compareTo(BigDecimal.valueOf(50000)) >= 0
                        || savingDeposit.compareTo(BigDecimal.valueOf(50000)) >= 0;

        boolean positiveBalance =
                debitDeposit.compareTo(debitWithdraw) > 0;

        if (hasDebit
                && enoughMoney
                && positiveBalance) {

            return Optional.of(
                    new RecommendationDto(
                            PRODUCT_ID,
                            PRODUCT_NAME,
                            PRODUCT_TEXT
                    )
            );
        }
        return Optional.empty();
    }
}
