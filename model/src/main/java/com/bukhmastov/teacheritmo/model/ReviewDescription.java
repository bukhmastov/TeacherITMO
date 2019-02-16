package com.bukhmastov.teacheritmo.model;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

@JsonAutoDetect(
        fieldVisibility = JsonAutoDetect.Visibility.ANY,
        getterVisibility = JsonAutoDetect.Visibility.NONE,
        setterVisibility = JsonAutoDetect.Visibility.NONE
)
public class ReviewDescription implements Serializable {

    @JsonProperty("criteria1")
    private ReviewCriteriaDescription criteria1;
    @JsonProperty("criteria2")
    private ReviewCriteriaDescription criteria2;
    @JsonProperty("criteria3")
    private ReviewCriteriaDescription criteria3;
    @JsonProperty("criteria4")
    private ReviewCriteriaDescription criteria4;
    @JsonProperty("criteria5")
    private ReviewCriteriaDescription criteria5;

    public ReviewDescription() {}

    public ReviewCriteriaDescription getCriteria1() {
        return criteria1;
    }

    public void setCriteria1(ReviewCriteriaDescription criteria1) {
        this.criteria1 = criteria1;
    }

    public ReviewCriteriaDescription getCriteria2() {
        return criteria2;
    }

    public void setCriteria2(ReviewCriteriaDescription criteria2) {
        this.criteria2 = criteria2;
    }

    public ReviewCriteriaDescription getCriteria3() {
        return criteria3;
    }

    public void setCriteria3(ReviewCriteriaDescription criteria3) {
        this.criteria3 = criteria3;
    }

    public ReviewCriteriaDescription getCriteria4() {
        return criteria4;
    }

    public void setCriteria4(ReviewCriteriaDescription criteria4) {
        this.criteria4 = criteria4;
    }

    public ReviewCriteriaDescription getCriteria5() {
        return criteria5;
    }

    public void setCriteria5(ReviewCriteriaDescription criteria5) {
        this.criteria5 = criteria5;
    }
}
