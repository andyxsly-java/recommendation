package teamproject.recommendation.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import teamproject.recommendation.dto.DynamicRuleDto;
import teamproject.recommendation.dto.DynamicRuleResponse;
import teamproject.recommendation.dto.RuleStatisticsResponse;
import teamproject.recommendation.service.DynamicRuleService;
import teamproject.recommendation.service.RuleStatisticService;

import java.util.UUID;

/**
 * REST-контроллер для управления динамическими правилами рекомендаций
 * и получения статистики их срабатывания.
 */
@Tag(
        name = "Dynamic Rules",
        description = "Управление динамическими правилами рекомендаций"
)
@RestController
@RequestMapping("/rule")
public class DynamicRuleController {

    private final DynamicRuleService dynamicRuleService;
    private final RuleStatisticService ruleStatisticService;

    public DynamicRuleController(DynamicRuleService dynamicRuleService, RuleStatisticService ruleStatisticService) {
        this.dynamicRuleService = dynamicRuleService;
        this.ruleStatisticService = ruleStatisticService;
    }
    /**
     * Создает новое динамическое правило.
     */
    @Operation(
            summary = "Создать правило",
            description = "Создает новое динамическое правило рекомендаций."
    )
    @ApiResponse(responseCode = "200", description = "Правило успешно создано")
    @PostMapping
    public DynamicRuleDto createRule(@RequestBody DynamicRuleDto dynamicRuleDto) {
        return dynamicRuleService.createRule(dynamicRuleDto);
    }
    /**
     * Возвращает список всех динамических правил.
     */
    @Operation(
            summary = "Получить список правил",
            description = "Возвращает список всех динамических правил."
    )
    @ApiResponse(responseCode = "200", description = "Список успешно получен")
    @GetMapping
    public DynamicRuleResponse getAllRules() {
        return new DynamicRuleResponse(dynamicRuleService.getAllRules());
    }

    /**
     * Возвращает статистику срабатывания правил.
     */
    @Operation(
            summary = "Получить статистику",
            description = "Возвращает статистику срабатывания всех правил."
    )
    @ApiResponse(
            responseCode = "200",
            description = "Статистика успешно получена",
            content = @Content(
                    examples = @ExampleObject(
                            value = """
                                    {
                                      "stats":[
                                        {
                                          "rule_id":"3fa85f64-5717-4562-b3fc-2c963f66afa6",
                                          "count":17
                                        }
                                      ]
                                    }
                                    """
                    )
            )
    )
    @GetMapping("/stats")
    public RuleStatisticsResponse getStatistics() {
        return ruleStatisticService.getStatistics();
    }
    /**
     * Удаляет динамическое правило.
     *
     * @param id идентификатор правила
     */
    @Operation(
            summary = "Удалить правило",
            description = "Удаляет правило и связанную с ним статистику."
    )
    @ApiResponse(responseCode = "204", description = "Правило успешно удалено")
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteRule(@PathVariable UUID id) {
        dynamicRuleService.deleteRule(id);
    }
}
