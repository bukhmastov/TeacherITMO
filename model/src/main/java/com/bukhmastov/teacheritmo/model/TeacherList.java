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
public class TeacherList implements Serializable {

    @JsonProperty("teachers")
    private List<Teacher> teachers;

    @JsonProperty("size")
    private long size;

    public TeacherList(List<Teacher> teachers) {
        this.teachers = teachers;
        this.size = teachers.size();
    }

    @Override
    public String toString() {
        return "TeacherList{" +
                "teachers=" + teachers +
                ", size=" + size +
                '}';
    }
}
