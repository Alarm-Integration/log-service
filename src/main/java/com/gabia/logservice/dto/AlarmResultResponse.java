package com.gabia.logservice.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.gabia.logservice.domain.log.AlarmRequestEntity;
import com.gabia.logservice.domain.log.AlarmResultEntity;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class AlarmResultResponse {
    @JsonProperty("appName")
    private String appName;

    @JsonProperty("logMessage")
    private String logMessage;

    @JsonProperty("address")
    private String address;

    @JsonProperty("isSuccess")
    private boolean isSuccess;

    @JsonProperty("createdAt")
    private LocalDateTime createdAt;

    public AlarmResultResponse(AlarmResultEntity alarmResultEntity, AlarmRequestEntity alarmRequestEntity) {
        this.appName = alarmResultEntity.getAppName();
        this.logMessage = alarmResultEntity.getLogMessage();
        this.address = alarmResultEntity.getAddress();
        this.isSuccess = alarmResultEntity.isSuccess();
        this.createdAt = alarmRequestEntity.getCreatedAt();
    }
}
