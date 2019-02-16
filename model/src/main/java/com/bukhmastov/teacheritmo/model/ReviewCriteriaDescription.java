package com.bukhmastov.teacheritmo.model;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

@JsonAutoDetect(
        fieldVisibility = JsonAutoDetect.Visibility.ANY,
        getterVisibility = JsonAutoDetect.Visibility.NONE,
        setterVisibility = JsonAutoDetect.Visibility.NONE
)
public class ReviewCriteriaDescription implements Serializable {

    @JsonProperty("name")
    private String name;
    @JsonProperty("0")
    private String desc0;
    @JsonProperty("5")
    private String desc5;

    public ReviewCriteriaDescription() {}

    public ReviewCriteriaDescription(String name, String desc0, String desc5) {
        this.name = name;
        this.desc0 = desc0;
        this.desc5 = desc5;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDesc0() {
        return desc0;
    }

    public void setDesc0(String desc0) {
        this.desc0 = desc0;
    }

    public String getDesc5() {
        return desc5;
    }

    public void setDesc5(String desc5) {
        this.desc5 = desc5;
    }
}
