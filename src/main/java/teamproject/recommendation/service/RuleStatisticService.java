package teamproject.recommendation.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import teamproject.recommendation.dto.RuleStatisticDto;
import teamproject.recommendation.dto.RuleStatisticsResponse;
import teamproject.recommendation.entity.RecommendationRuleEntity;
import teamproject.recommendation.entity.RuleStatisticEntity;
import teamproject.recommendation.repository.RuleStatisticRepository;

import java.util.List;
import java.util.UUID;
/**
 * Сервис для хранения и получения статистики
 * срабатывания динамических правил рекомендаций.
 */
@Service
public class RuleStatisticService {

    private final RuleStatisticRepository ruleStatisticRepository;

    public RuleStatisticService(RuleStatisticRepository ruleStatisticRepository) {
        this.ruleStatisticRepository = ruleStatisticRepository;
    }
    /**
     * Создает запись статистики для нового правила.
     *
     * @param rule правило рекомендаций
     */
    @Transactional
    public void createStatistic(RecommendationRuleEntity rule) {

        RuleStatisticEntity statistic = new RuleStatisticEntity(rule);

        ruleStatisticRepository.save(statistic);
    }
    /**
     * Увеличивает счетчик срабатываний указанного правила.
     *
     * @param ruleId идентификатор правила
     */
    // Атомарное увеличение счетчика выполняется
    // непосредственно на стороне базы данных.
    @Transactional
    public void increment(UUID ruleId) {
        ruleStatisticRepository.increment(ruleId);
    }
    /**
     * Удаляет статистику для указанного правила.
     *
     * @param ruleId идентификатор правила
     */
    @Transactional
    public void deleteStatistic(UUID ruleId) {
        ruleStatisticRepository.deleteById(ruleId);
    }
    /**
     * Возвращает все записи статистики.
     *
     * @return список записей статистики
     */
    public List<RuleStatisticEntity> getAllStatistics() {
        return ruleStatisticRepository.findAll();
    }
    /**
     * Возвращает статистику срабатывания всех правил.
     *
     * @return статистика по всем правилам
     */
    public RuleStatisticsResponse getStatistics() {

        List<RuleStatisticDto> statistics =
                ruleStatisticRepository.findAll()
                        .stream()
                        .map(stat -> new RuleStatisticDto(
                                stat.getRule().getId(),
                                stat.getCount()
                        ))
                        .toList();

        return new RuleStatisticsResponse(statistics);
    }
}
