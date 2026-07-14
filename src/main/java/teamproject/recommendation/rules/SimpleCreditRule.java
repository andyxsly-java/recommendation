package teamproject.recommendation.rules;

import org.springframework.stereotype.Component;
import teamproject.recommendation.dto.RecommendationDto;
import teamproject.recommendation.repository.RecommendationRepository;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.UUID;

@Component
public class SimpleCreditRule implements RecommendationRuleSet {

    private static final UUID PRODUCT_ID =
            UUID.fromString("ab138afb-f3ba-4a93-b74f-0fcee86d447f");

    private static final String PRODUCT_NAME = "Простой кредит";

    private static final String PRODUCT_TEXT =
            """
                    Откройте мир выгодных кредитов с нами!
                    Ищете способ быстро и без лишних хлопот получить нужную сумму? Тогда наш выгодный кредит — именно то, что вам нужно! 
                    Мы предлагаем низкие процентные ставки, гибкие условия и индивидуальный подход к каждому клиенту.
                    Почему выбирают нас:
                    Быстрое рассмотрение заявки. Мы ценим ваше время, поэтому процесс рассмотрения заявки занимает всего несколько часов.
                    Удобное оформление. Подать заявку на кредит можно онлайн на нашем сайте или в мобильном приложении.
                    Широкий выбор кредитных продуктов. Мы предлагаем кредиты на различные цели: покупку недвижимости, автомобиля, образование, лечение и многое другое.
                    Не упустите возможность воспользоваться выгодными условиями кредитования от нашей компании!
            """;

    private final RecommendationRepository repository;

    public SimpleCreditRule(RecommendationRepository repository) {
        this.repository = repository;
    }

    @Override
    public Optional<RecommendationDto> check(UUID userId) {

        boolean hasCredit = repository.hasProduct(userId, "CREDIT");

        BigDecimal debitDeposit =
                repository.getDepositSum(userId, "DEBIT");

        BigDecimal debitWithdraw =
                repository.getWithdrawSum(userId, "DEBIT");

        boolean depositGreaterThanWithdraw =
                debitDeposit.compareTo(debitWithdraw) > 0;

        boolean largeExpenses =
                debitWithdraw.compareTo(BigDecimal.valueOf(100000)) > 0;

        if (!hasCredit
                && depositGreaterThanWithdraw
                && largeExpenses) {

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
