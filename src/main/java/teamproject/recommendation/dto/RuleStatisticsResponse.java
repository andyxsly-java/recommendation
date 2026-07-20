package teamproject.recommendation.dto;

import java.util.List;

public class RuleStatisticsResponse {

    private List<RuleStatisticDto> stats;

    public RuleStatisticsResponse() {
    }

    public RuleStatisticsResponse(List<RuleStatisticDto> stats) {
        this.stats = stats;
    }

    public List<RuleStatisticDto> getStats() {
        return stats;
    }

    public void setStats(List<RuleStatisticDto> stats) {
        this.stats = stats;
    }
}
