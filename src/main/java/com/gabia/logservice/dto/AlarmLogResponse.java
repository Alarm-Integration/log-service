package com.gabia.logservice.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.gabia.logservice.domain.log.AlarmLogEntity;
import com.gabia.logservice.domain.log.AlarmMessageEntity;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class AlarmLogResponse {

    @JsonProperty("appName")
    private String appName;

    @JsonProperty("logMessage")
    private String logMessage;

    @JsonProperty("receiver")
    private String receiver;

    @JsonProperty("isSuccess")
    private boolean isSuccess;

    @JsonProperty("createdAt")
    private LocalDateTime createdAt;

    public AlarmLogResponse(AlarmLogEntity alarmLogEntity) {
        this.appName = alarmLogEntity.getAppName();
        this.logMessage = alarmLogEntity.getLogMessage();
        this.receiver = alarmLogEntity.getReceiver();
        this.isSuccess = alarmLogEntity.ge();
        this.appName = alarmLogEntity.getAppName();
        this.resultMsg = logEntity.getResultMsg();
    }

}
