package com.bukhmastov.teacheritmo.model;

import com.bukhmastov.teacheritmo.marker.HasId;

import java.io.Serializable;
import java.sql.Timestamp;

public class ReviewLock implements HasId, Serializable {

    private Long id;
    private String ip;
    private Integer teacherExtId;
    private Timestamp created;

    public ReviewLock() {}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public Integer getTeacherExtId() {
        return teacherExtId;
    }

    public void setTeacherExtId(Integer teacherExtId) {
        this.teacherExtId = teacherExtId;
    }

    public Timestamp getCreated() {
        return created;
    }

    public void setCreated(Timestamp created) {
        this.created = created;
    }

    @Override
    public String toString() {
        return "ReviewLock{" +
                "id=" + id +
                ", ip='" + ip + '\'' +
                ", teacherExtId=" + teacherExtId +
                ", created=" + created +
                '}';
    }
}
