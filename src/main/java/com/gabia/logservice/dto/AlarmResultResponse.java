package com.gabia.logservice.dto;

import com.gabia.logservice.domain.log.LogEntity;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class AlarmResultResponse {
    private String appName;
    private String resultMsg;
    private LocalDateTime createdAt;

    public AlarmResultResponse(LogEntity logEntity) {
        this.appName = logEntity.getAppName();
        this.resultMsg = logEntity.getResultMsg();
        this.createdAt = logEntity.getCreatedAt();
    }

}
