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
public class ReviewSummary implements Serializable {

    @JsonProperty("teacher")
    private Teacher teacher;

    @JsonProperty("criteria1")
    private ReviewCriteria criteria1;

    @JsonProperty("criteria2")
    private ReviewCriteria criteria2;

    @JsonProperty("criteria3")
    private ReviewCriteria criteria3;

    @JsonProperty("criteria4")
    private ReviewCriteria criteria4;

    @JsonProperty("criteria5")
    private ReviewCriteria criteria5;

    @JsonProperty("comments")
    private List<String> comments;

    @JsonProperty("commentsSize")
    private long commentsSize = 0;

    public ReviewSummary() {}

    public Teacher getTeacher() {
        return teacher;
    }

    public void setTeacher(Teacher teacher) {
        this.teacher = teacher;
    }

    public ReviewCriteria getCriteria1() {
        return criteria1;
    }

    public void setCriteria1(ReviewCriteria criteria1) {
        this.criteria1 = criteria1;
    }

    public ReviewCriteria getCriteria2() {
        return criteria2;
    }

    public void setCriteria2(ReviewCriteria criteria2) {
        this.criteria2 = criteria2;
    }

    public ReviewCriteria getCriteria3() {
        return criteria3;
    }

    public void setCriteria3(ReviewCriteria criteria3) {
        this.criteria3 = criteria3;
    }

    public ReviewCriteria getCriteria4() {
        return criteria4;
    }

    public void setCriteria4(ReviewCriteria criteria4) {
        this.criteria4 = criteria4;
    }

    public ReviewCriteria getCriteria5() {
        return criteria5;
    }

    public void setCriteria5(ReviewCriteria criteria5) {
        this.criteria5 = criteria5;
    }

    public List<String> getComments() {
        return comments;
    }

    public void setComments(List<String> comments) {
        this.comments = comments;
    }

    public long getCommentsSize() {
        return commentsSize;
    }

    public void setCommentsSize(long commentsSize) {
        this.commentsSize = commentsSize;
    }

    @Override
    public String toString() {
        return "ReviewSummary{" +
                "teacher=" + teacher +
                ", criteria1=" + criteria1 +
                ", criteria2=" + criteria2 +
                ", criteria3=" + criteria3 +
                ", criteria4=" + criteria4 +
                ", criteria5=" + criteria5 +
                ", comments=" + comments +
                ", commentsSize=" + commentsSize +
                '}';
    }
}
