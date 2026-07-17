package teamproject.recommendation.entity;

import jakarta.persistence.*;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "recommendation_rule")
public class RecommendationRuleEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "product_name", nullable = false)
    private String productName;

    @Column(name = "product_id", nullable = false)
    private UUID productId;

    @Column(name = "product_text", nullable = false, length = 2000)
    private String productText;

    @JdbcTypeCode(SqlTypes.JSON)
    @Column(name = "rule", columnDefinition = "jsonb", nullable = false)
    private List<RuleQuery> rule = new ArrayList<>();

    public RecommendationRuleEntity() {
    }

    public RecommendationRuleEntity(String productName,
                                    UUID productId,
                                    String productText,
                                    List<RuleQuery> rule) {
        this.productName = productName;
        this.productId = productId;
        this.productText = productText;
        this.rule = rule;
    }

    public UUID getId() {
        return id;
    }

    public String getProductName() {
        return productName;
    }

    public UUID getProductId() {
        return productId;
    }

    public String getProductText() {
        return productText;
    }

    public List<RuleQuery> getRule() {
        return rule;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public void setProductId(UUID productId) {
        this.productId = productId;
    }

    public void setProductText(String productText) {
        this.productText = productText;
    }

    public void setRule(List<RuleQuery> rule) {
        this.rule = rule;
    }
}
