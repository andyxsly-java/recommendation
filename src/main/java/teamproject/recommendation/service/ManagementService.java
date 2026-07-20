package teamproject.recommendation.service;

import org.springframework.stereotype.Service;

import org.springframework.boot.info.BuildProperties;
import teamproject.recommendation.dto.ManagementInfoDto;
import teamproject.recommendation.repository.RecommendationRepository;

@Service
public class ManagementService {

    private final RecommendationRepository recommendationRepository;

    private final BuildProperties buildProperties;

    public ManagementService(RecommendationRepository recommendationRepository, BuildProperties buildProperties) {
        this.recommendationRepository = recommendationRepository;
        this.buildProperties = buildProperties;
    }

    public void clearCaches() {
        recommendationRepository.clearCaches();
    }

    public ManagementInfoDto getInfo() {
        return new ManagementInfoDto(
                buildProperties.getName(),
                buildProperties.getVersion()
        );
    }
}
