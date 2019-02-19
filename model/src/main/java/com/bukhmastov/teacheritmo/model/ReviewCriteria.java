package com.bukhmastov.teacheritmo.model;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

@JsonAutoDetect(
        fieldVisibility = JsonAutoDetect.Visibility.ANY,
        getterVisibility = JsonAutoDetect.Visibility.NONE,
        setterVisibility = JsonAutoDetect.Visibility.NONE
)
public class ReviewCriteria implements Serializable {

    @JsonProperty("value")
    private Double value;

    @JsonProperty("total")
    private long total;

    public ReviewCriteria() {}

    public ReviewCriteria(Double value, long total) {
        this.value = value;
        this.total = total;
    }

    public Double getValue() {
        return value;
    }

    public void setValue(Double value) {
        this.value = value;
    }

    public long getTotal() {
        return total;
    }

    public void setTotal(long total) {
        this.total = total;
    }

    @Override
    public String toString() {
        return "ReviewCriteria{" +
                "value=" + value +
                ", total=" + total +
                '}';
    }
}
