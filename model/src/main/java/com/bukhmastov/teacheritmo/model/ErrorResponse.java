package com.bukhmastov.teacheritmo.model;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonAutoDetect(
        fieldVisibility = JsonAutoDetect.Visibility.ANY,
        getterVisibility = JsonAutoDetect.Visibility.NONE,
        setterVisibility = JsonAutoDetect.Visibility.NONE
)
public class ErrorResponse {

    @JsonProperty("status")
    private String httpStatus;

    @JsonProperty("reason")
    private String reason;

    public ErrorResponse() {}

    public ErrorResponse(String httpStatus, String reason) {
        this.httpStatus = httpStatus;
        this.reason = reason;
    }

    public String getHttpStatus() {
        return httpStatus;
    }

    public void setHttpStatus(String httpStatus) {
        this.httpStatus = httpStatus;
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
                "httpStatus='" + httpStatus + '\'' +
                ", reason='" + reason + '\'' +
                '}';
    }
}
