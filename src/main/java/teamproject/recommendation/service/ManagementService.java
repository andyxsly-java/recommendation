package teamproject.recommendation.service;

import org.springframework.stereotype.Service;

import org.springframework.boot.info.BuildProperties;
import teamproject.recommendation.dto.ManagementInfoDto;
import teamproject.recommendation.repository.RecommendationRepository;
/**
 * Сервис технологических операций приложения.
 */
@Service
public class ManagementService {

    private final RecommendationRepository recommendationRepository;

    private final BuildProperties buildProperties;

    public ManagementService(RecommendationRepository recommendationRepository, BuildProperties buildProperties) {
        this.recommendationRepository = recommendationRepository;
        this.buildProperties = buildProperties;
    }
    /**
     * Очищает все кэши рекомендаций.
     */
    public void clearCaches() {
        recommendationRepository.clearCaches();
    }
    /**
     * Возвращает информацию о приложении.
     *
     * @return название и версия сервиса
     */
    public ManagementInfoDto getInfo() {
        return new ManagementInfoDto(
                buildProperties.getName(),
                buildProperties.getVersion()
        );
    }
}
