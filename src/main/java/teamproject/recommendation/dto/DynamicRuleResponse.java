package teamproject.recommendation.dto;

import java.util.List;

public class DynamicRuleResponse {

    private List<DynamicRuleDto> data;

    public DynamicRuleResponse() {
    }

    public DynamicRuleResponse(List<DynamicRuleDto> data) {
        this.data = data;
    }

    public List<DynamicRuleDto> getData() {
        return data;
    }

    public void setData(List<DynamicRuleDto> data) {
        this.data = data;
    }
}
