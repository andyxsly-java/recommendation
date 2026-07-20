package teamproject.recommendation.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import teamproject.recommendation.dto.DynamicRuleDto;
import teamproject.recommendation.dto.DynamicRuleResponse;
import teamproject.recommendation.dto.RuleStatisticsResponse;
import teamproject.recommendation.service.DynamicRuleService;
import teamproject.recommendation.service.RuleStatisticService;

import java.util.UUID;

@RestController
@RequestMapping("/rule")
public class DynamicRuleController {

    private final DynamicRuleService dynamicRuleService;
    private final RuleStatisticService ruleStatisticService;

    public DynamicRuleController(DynamicRuleService dynamicRuleService, RuleStatisticService ruleStatisticService) {
        this.dynamicRuleService = dynamicRuleService;
        this.ruleStatisticService = ruleStatisticService;
    }

    @PostMapping
    public DynamicRuleDto createRule(@RequestBody DynamicRuleDto dynamicRuleDto) {
        return dynamicRuleService.createRule(dynamicRuleDto);
    }

    @GetMapping
    public DynamicRuleResponse getAllRules() {
        return new DynamicRuleResponse(dynamicRuleService.getAllRules());
    }

    @GetMapping("/rule/stats")
    public RuleStatisticsResponse getStatistics() {
        return ruleStatisticService.getStatistics();
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteRule(@PathVariable UUID id) {
        dynamicRuleService.deleteRule(id);
    }
}
