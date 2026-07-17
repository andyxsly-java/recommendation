package teamproject.recommendation.dto;

import java.util.List;
import java.util.UUID;

public class DynamicRuleDto {

    private UUID id;

    private String productName;

    private UUID productId;

    private String productText;

    private List<RuleDto> rule;

    public DynamicRuleDto() {
    }

    public DynamicRuleDto(UUID id,
                                 String productName,
                                 UUID productId,
                                 String productText,
                                 List<RuleDto> rule) {
        this.id = id;
        this.productName = productName;
        this.productId = productId;
        this.productText = productText;
        this.rule = rule;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public UUID getProductId() {
        return productId;
    }

    public void setProductId(UUID productId) {
        this.productId = productId;
    }

    public String getProductText() {
        return productText;
    }

    public void setProductText(String productText) {
        this.productText = productText;
    }

    public List<RuleDto> getRule() {
        return rule;
    }

    public void setRule(List<RuleDto> rule) {
        this.rule = rule;
    }
}

