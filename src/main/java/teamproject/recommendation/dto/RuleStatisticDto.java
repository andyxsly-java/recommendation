package teamproject.recommendation.dto;

import java.util.UUID;

public class RuleStatisticDto {

    private UUID rule_id;

    private long count;

    public RuleStatisticDto() {
    }

    public RuleStatisticDto(UUID rule_id, long count) {
        this.rule_id = rule_id;
        this.count = count;
    }

    public UUID getRuleId() {
        return rule_id;
    }

    public void setRuleId(UUID rule_id) {
        this.rule_id = rule_id;
    }

    public long getCount() {
        return count;
    }

    public void setCount(long count) {
        this.count = count;
    }
}
