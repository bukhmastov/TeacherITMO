package com.bukhmastov.teacheritmo.model.itmo;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.util.List;

@JsonAutoDetect(
        fieldVisibility = JsonAutoDetect.Visibility.ANY,
        getterVisibility = JsonAutoDetect.Visibility.NONE,
        setterVisibility = JsonAutoDetect.Visibility.NONE
)
@JsonIgnoreProperties(ignoreUnknown = true)
public class ItmoTeacherList implements Serializable {

    @JsonProperty("count")
    private Integer count;

    @JsonProperty("lastname")
    private String lastName;

    @JsonProperty("limit")
    private Integer limit;

    @JsonProperty("offset")
    private Integer offset;

    @JsonProperty("list")
    private List<ItmoTeacher> teacherList;

    public ItmoTeacherList() {}

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Integer getLimit() {
        return limit;
    }

    public void setLimit(Integer limit) {
        this.limit = limit;
    }

    public Integer getOffset() {
        return offset;
    }

    public void setOffset(Integer offset) {
        this.offset = offset;
    }

    public List<ItmoTeacher> getTeacherList() {
        return teacherList;
    }

    public void setTeacherList(List<ItmoTeacher> teacherList) {
        this.teacherList = teacherList;
    }

    @Override
    public String toString() {
        return "ItmoTeacherList{" +
                "count=" + count +
                ", lastName='" + lastName + '\'' +
                ", limit=" + limit +
                ", offset=" + offset +
                ", teacherList=" + teacherList +
                '}';
    }
}
