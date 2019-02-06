package com.bukhmastov.teacheritmo.model;

import com.bukhmastov.teacheritmo.marker.HasId;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.sql.Timestamp;

@JsonAutoDetect(
        fieldVisibility = JsonAutoDetect.Visibility.ANY,
        getterVisibility = JsonAutoDetect.Visibility.NONE,
        setterVisibility = JsonAutoDetect.Visibility.NONE
)
public class Review implements HasId, Serializable {

    @JsonProperty("id")
    private Long id;

    @JsonProperty("teacherExtId")
    private Integer teacherExtId;

    @JsonProperty("comment")
    private String comment;

    @JsonProperty("criteria1")
    private Integer criteria1;

    @JsonProperty("criteria2")
    private Integer criteria2;

    @JsonProperty("criteria3")
    private Integer criteria3;

    @JsonProperty("criteria4")
    private Integer criteria4;

    @JsonProperty("criteria5")
    private Integer criteria5;

    @JsonIgnore
    private Timestamp created;

    public Review() {}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getTeacherExtId() {
        return teacherExtId;
    }

    public void setTeacherExtId(Integer teacherExtId) {
        this.teacherExtId = teacherExtId;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Integer getCriteria1() {
        return criteria1;
    }

    public void setCriteria1(Integer criteria1) {
        this.criteria1 = criteria1;
    }

    public Integer getCriteria2() {
        return criteria2;
    }

    public void setCriteria2(Integer criteria2) {
        this.criteria2 = criteria2;
    }

    public Integer getCriteria3() {
        return criteria3;
    }

    public void setCriteria3(Integer criteria3) {
        this.criteria3 = criteria3;
    }

    public Integer getCriteria4() {
        return criteria4;
    }

    public void setCriteria4(Integer criteria4) {
        this.criteria4 = criteria4;
    }

    public Integer getCriteria5() {
        return criteria5;
    }

    public void setCriteria5(Integer criteria5) {
        this.criteria5 = criteria5;
    }

    public Timestamp getCreated() {
        return created;
    }

    public void setCreated(Timestamp created) {
        this.created = created;
    }

    @Override
    public String toString() {
        return "Review{" +
                "id=" + id +
                ", teacherExtId=" + teacherExtId +
                ", comment='" + comment + '\'' +
                ", criteria1=" + criteria1 +
                ", criteria2=" + criteria2 +
                ", criteria3=" + criteria3 +
                ", criteria4=" + criteria4 +
                ", criteria5=" + criteria5 +
                ", created=" + created +
                '}';
    }
}
