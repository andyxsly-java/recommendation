package teamproject.recommendation.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import teamproject.recommendation.dto.ManagementInfoDto;
import teamproject.recommendation.service.ManagementService;

@RestController
@RequestMapping("/management")
public class ManagementController {

    private final ManagementService managementService;

    public ManagementController(ManagementService managementService) {
        this.managementService = managementService;
    }

    @GetMapping("/info")
    public ManagementInfoDto getInfo() {
        return managementService.getInfo();
    }

    @PostMapping("/clear-caches")
    @ResponseStatus(HttpStatus.OK)
    public void clearCaches() {
        managementService.clearCaches();
    }
}
