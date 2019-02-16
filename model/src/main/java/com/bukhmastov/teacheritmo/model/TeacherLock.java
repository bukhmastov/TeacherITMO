package com.bukhmastov.teacheritmo.model;

import com.bukhmastov.teacheritmo.marker.HasId;

import java.io.Serializable;
import java.sql.Timestamp;

public class TeacherLock implements HasId, Serializable {

    private Long id;
    private String ip;
    private Timestamp created;

    public TeacherLock() {}

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

    public Timestamp getCreated() {
        return created;
    }

    public void setCreated(Timestamp created) {
        this.created = created;
    }

    @Override
    public String toString() {
        return "TeacherLock{" +
                "id=" + id +
                ", ip='" + ip + '\'' +
                ", created=" + created +
                '}';
    }
}
