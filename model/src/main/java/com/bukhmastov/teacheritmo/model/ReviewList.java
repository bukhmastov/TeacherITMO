package com.bukhmastov.teacheritmo.model;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.util.List;

@JsonAutoDetect(
        fieldVisibility = JsonAutoDetect.Visibility.ANY,
        getterVisibility = JsonAutoDetect.Visibility.NONE,
        setterVisibility = JsonAutoDetect.Visibility.NONE
)
public class ReviewList implements Serializable {

    @JsonProperty("reviews")
    private List<Review> reviews;

    @JsonProperty("size")
    private long size;

    public ReviewList(List<Review> reviews) {
        this.reviews = reviews;
        this.size = reviews.size();
    }

    @Override
    public String toString() {
        return "ReviewList{" +
                "reviews=" + reviews +
                ", size=" + size +
                '}';
    }
}
