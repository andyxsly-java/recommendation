package teamproject.recommendation.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import teamproject.recommendation.dto.DynamicRuleDto;
import teamproject.recommendation.dto.DynamicRuleResponse;
import teamproject.recommendation.service.DynamicRuleService;

import java.util.UUID;

@RestController
@RequestMapping("/rule")
public class DynamicRuleController {

    private final DynamicRuleService dynamicRuleService;

    public DynamicRuleController(DynamicRuleService dynamicRuleService) {
        this.dynamicRuleService = dynamicRuleService;
    }

    @PostMapping
    public DynamicRuleDto createRule(@RequestBody DynamicRuleDto dynamicRuleDto) {
        return dynamicRuleService.createRule(dynamicRuleDto);
    }

    @GetMapping
    public DynamicRuleResponse getAllRules() {
        return new DynamicRuleResponse(dynamicRuleService.getAllRules());
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteRule(@PathVariable UUID id) {
        dynamicRuleService.deleteRule(id);
    }
}
