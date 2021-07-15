package com.gabia.logservice.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.gabia.logservice.domain.log.LogEntity;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class AlarmResultResponse {
    @JsonProperty("app_name")
    private String appName;

    @JsonProperty("result_msg")
    private String resultMsg;

    @JsonProperty("created_at")
    private LocalDateTime createdAt;

    public AlarmResultResponse(LogEntity logEntity) {
        this.appName = logEntity.getAppName();
        this.resultMsg = logEntity.getResultMsg();
        this.createdAt = logEntity.getCreatedAt();
    }
}
