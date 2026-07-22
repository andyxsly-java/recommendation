package teamproject.recommendation.dto;

import java.util.List;
import java.util.UUID;

public class DynamicRuleDto {

    private UUID id;

    private String product_name;

    private UUID product_id;

    private String product_text;

    private List<RuleDto> rule;

    public DynamicRuleDto() {
    }

    public DynamicRuleDto(UUID id,
                                 String product_name,
                                 UUID product_id,
                                 String product_text,
                                 List<RuleDto> rule) {
        this.id = id;
        this.product_name = product_name;
        this.product_id = product_id;
        this.product_text = product_text;
        this.rule = rule;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getProductName() {
        return product_name;
    }

    public void setProductName(String product_name) {
        this.product_name = product_name;
    }

    public UUID getProductId() {
        return product_id;
    }

    public void setProductId(UUID product_id) {
        this.product_id = product_id;
    }

    public String getProductText() {
        return product_text;
    }

    public void setProductText(String product_text) {
        this.product_text = this.product_text;
    }

    public List<RuleDto> getRule() {
        return rule;
    }

    public void setRule(List<RuleDto> rule) {
        this.rule = rule;
    }
}

