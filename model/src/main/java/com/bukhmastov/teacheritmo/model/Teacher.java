package com.bukhmastov.teacheritmo.model;

import com.bukhmastov.teacheritmo.dict.EnSource;
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
public class Teacher implements HasId, Serializable {

    @JsonIgnore
    private Long id;

    @JsonProperty("id")
    private Integer extId;

    @JsonProperty("name")
    private String name;

    @JsonProperty("post")
    private String post;

    @JsonIgnore
    private EnSource source;

    @JsonIgnore
    private Timestamp created;

    public Teacher() {}

    public Teacher(int extId, String name) {
        this.extId = extId;
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getExtId() {
        return extId;
    }

    public void setExtId(Integer ext_id) {
        this.extId = ext_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPost() {
        return post;
    }

    public void setPost(String post) {
        this.post = post;
    }

    public EnSource getSource() {
        return source;
    }

    public void setSource(EnSource source) {
        this.source = source;
    }

    public Timestamp getCreated() {
        return created;
    }

    public void setCreated(Timestamp created) {
        this.created = created;
    }

    @Override
    public String toString() {
        return "Teacher{" +
                "id=" + id +
                ", extId=" + extId +
                ", name='" + name + '\'' +
                ", post='" + post + '\'' +
                ", source=" + source +
                ", created=" + created +
                '}';
    }
}
