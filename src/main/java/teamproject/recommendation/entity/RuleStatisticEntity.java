package teamproject.recommendation.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "rule_statistic")
public class RuleStatisticEntity {

    @Id
    @OneToOne
    @JoinColumn(name = "rule_id", nullable = false)
    private RecommendationRuleEntity rule;

    @Column(name = "count", nullable = false)
    private long count;

    public RuleStatisticEntity() {
    }

    public RuleStatisticEntity(RecommendationRuleEntity rule) {
        this.rule = rule;
        this.count = 0;
    }

    public RecommendationRuleEntity getRule() {
        return rule;
    }

    public void setRule(RecommendationRuleEntity rule) {
        this.rule = rule;
    }

    public long getCount() {
        return count;
    }

    public void setCount(long count) {
        this.count = count;
    }
}
