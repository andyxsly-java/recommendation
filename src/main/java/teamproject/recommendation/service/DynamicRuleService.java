package teamproject.recommendation.service;

import org.springframework.stereotype.Service;
import teamproject.recommendation.dto.DynamicRuleDto;
import teamproject.recommendation.dto.RuleDto;
import teamproject.recommendation.entity.RecommendationRuleEntity;
import teamproject.recommendation.entity.RuleQuery;
import teamproject.recommendation.repository.DynamicRuleRepository;

import java.util.List;
import java.util.UUID;
/**
 * Сервис управления динамическими правилами рекомендаций.
 */
@Service
public class DynamicRuleService {

    private final DynamicRuleRepository dynamicRuleRepository;

    private final RuleStatisticService ruleStatisticService;

    public DynamicRuleService(DynamicRuleRepository dynamicRuleRepository, RuleStatisticService ruleStatisticService) {
        this.dynamicRuleRepository = dynamicRuleRepository;
        this.ruleStatisticService = ruleStatisticService;
    }
    /**
     * Создает новое динамическое правило.
     *
     * @param dto данные нового правила
     * @return созданное правило
     */
    public DynamicRuleDto createRule(DynamicRuleDto dto) {

        RecommendationRuleEntity entity = new RecommendationRuleEntity();
        entity.setProductName(dto.getProductName());
        entity.setProductId(dto.getProductId());
        entity.setProductText(dto.getProductText());

        entity.setRule(dto.getRule()
                .stream()
                .map(this::toRuleQuery)
                .toList());

        RecommendationRuleEntity saved = dynamicRuleRepository.save(entity);

        // Для нового правила сразу создается запись статистики
        // с начальным значением счетчика 0.
        ruleStatisticService.createStatistic(saved);

        return toDto(saved);
    }
    /**
     * Возвращает список всех динамических правил.
     *
     * @return список правил
     */
    public List<DynamicRuleDto> getAllRules() {
        return dynamicRuleRepository.findAll()
                .stream()
                .map(this::toDto)
                .toList();
    }
    /**
     * Удаляет динамическое правило и связанную с ним статистику.
     *
     * @param id идентификатор правила
     */
    public void deleteRule(UUID id) {
        // Сначала удаляем статистику,
        // затем само правило.
        ruleStatisticService.deleteStatistic(id);

        dynamicRuleRepository.deleteById(id);
    }

    private RuleQuery toRuleQuery(RuleDto dto) {

        RuleQuery query = new RuleQuery();
        query.setQuery(dto.getQuery());
        query.setArguments(dto.getArguments());
        query.setNegate(dto.isNegate());

        return query;
    }

    private DynamicRuleDto toDto(RecommendationRuleEntity entity) {

        List<RuleDto> rules = entity.getRule()
                .stream()
                .map(rule -> new RuleDto(
                        rule.getQuery(),
                        rule.getArguments(),
                        rule.isNegate()))
                .toList();

        return new DynamicRuleDto(
                entity.getId(),
                entity.getProductName(),
                entity.getProductId(),
                entity.getProductText(),
                rules
        );
    }
}
