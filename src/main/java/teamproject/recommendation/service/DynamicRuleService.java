package teamproject.recommendation.service;

import org.springframework.stereotype.Service;
import teamproject.recommendation.dto.DynamicRuleDto;
import teamproject.recommendation.dto.RuleDto;
import teamproject.recommendation.entity.RecommendationRuleEntity;
import teamproject.recommendation.entity.RuleQuery;
import teamproject.recommendation.repository.DynamicRuleRepository;

import java.util.List;
import java.util.UUID;

@Service
public class DynamicRuleService {

    private final DynamicRuleRepository dynamicRuleRepository;

    private final RuleStatisticService ruleStatisticService;

    public DynamicRuleService(DynamicRuleRepository dynamicRuleRepository, RuleStatisticService ruleStatisticService) {
        this.dynamicRuleRepository = dynamicRuleRepository;
        this.ruleStatisticService = ruleStatisticService;
    }

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
        ruleStatisticService.createStatistic(saved);

        return toDto(saved);
    }

    public List<DynamicRuleDto> getAllRules() {
        return dynamicRuleRepository.findAll()
                .stream()
                .map(this::toDto)
                .toList();
    }

    public void deleteRule(UUID id) {
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
