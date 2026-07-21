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

@Service
public class RuleStatisticService {

    private final RuleStatisticRepository ruleStatisticRepository;

    public RuleStatisticService(RuleStatisticRepository ruleStatisticRepository) {
        this.ruleStatisticRepository = ruleStatisticRepository;
    }

    @Transactional
    public void createStatistic(RecommendationRuleEntity rule) {

        RuleStatisticEntity statistic = new RuleStatisticEntity(rule);

        ruleStatisticRepository.save(statistic);
    }

    @Transactional
    public void increment(UUID ruleId) {
        ruleStatisticRepository.increment(ruleId);
    }

    @Transactional
    public void deleteStatistic(UUID ruleId) {
        ruleStatisticRepository.deleteById(ruleId);
    }

    public List<RuleStatisticEntity> getAllStatistics() {
        return ruleStatisticRepository.findAll();
    }

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
