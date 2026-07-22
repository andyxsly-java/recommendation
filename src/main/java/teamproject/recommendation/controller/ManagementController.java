package teamproject.recommendation.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import teamproject.recommendation.dto.ManagementInfoDto;
import teamproject.recommendation.service.ManagementService;
/**
 * REST-контроллер управления приложением.
 */
@Tag(
        name = "Management",
        description = "Служебные методы управления приложением"
)
@RestController
@RequestMapping("/management")
public class ManagementController {

    private final ManagementService managementService;

    public ManagementController(ManagementService managementService) {
        this.managementService = managementService;
    }

    @Operation(
            summary = "Информация о сервисе",
            description = "Возвращает название и версию приложения."
    )
    @ApiResponse(
            responseCode = "200",
            description = "Информация успешно получена",
            content = @Content(
                    examples = @ExampleObject(
                            value = """
                                    {
                                      "name":"recommendation-service",
                                      "version":"1.0.0"
                                    }
                                    """
                    )))
    @GetMapping("/info")
    public ManagementInfoDto getInfo() {
        return managementService.getInfo();
    }
    @Operation(
            summary = "Очистить кеш",
            description = "Полностью очищает кеш рекомендаций."
    )
    @ApiResponse(responseCode = "204", description = "Кеш успешно очищен")
    @PostMapping("/clear-caches")
    @ResponseStatus(HttpStatus.OK)
    public void clearCaches() {
        managementService.clearCaches();
    }
}
