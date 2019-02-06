package com.bukhmastov.teacheritmo.model.itmo;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

@JsonAutoDetect(
        fieldVisibility = JsonAutoDetect.Visibility.ANY,
        getterVisibility = JsonAutoDetect.Visibility.NONE,
        setterVisibility = JsonAutoDetect.Visibility.NONE
)
@JsonIgnoreProperties(ignoreUnknown = true)
public class ItmoTeacher implements Serializable {

    @JsonProperty("person")
    private String person;

    @JsonProperty("pid")
    private Integer pid;

    @JsonProperty("post")
    private String post;

    public ItmoTeacher() {}

    public String getPerson() {
        return person;
    }

    public void setPerson(String person) {
        this.person = person;
    }

    public Integer getPid() {
        return pid;
    }

    public void setPid(Integer pid) {
        this.pid = pid;
    }

    public String getPost() {
        return post;
    }

    public void setPost(String post) {
        this.post = post;
    }

    @Override
    public String toString() {
        return "ItmoTeacher{" +
                "person='" + person + '\'' +
                ", pid=" + pid +
                ", post='" + post + '\'' +
                '}';
    }
}
