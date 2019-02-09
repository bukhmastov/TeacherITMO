package com.bukhmastov.teacheritmo.model;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

@JsonAutoDetect(
        fieldVisibility = JsonAutoDetect.Visibility.ANY,
        getterVisibility = JsonAutoDetect.Visibility.NONE,
        setterVisibility = JsonAutoDetect.Visibility.NONE
)
public class ResponseError implements Serializable {

    @JsonProperty("status")
    private Integer status;

    @JsonProperty("description")
    private String description;

    @JsonProperty("reason")
    private String reason;

    public ResponseError() {}

    public ResponseError(Integer status, String description, String reason) {
        this.status = status;
        this.description = description;
        this.reason = reason;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    @Override
    public String toString() {
        return "ErrorResponse{" +
                "status=" + status +
                ", reason='" + reason + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
