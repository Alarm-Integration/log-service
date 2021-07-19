package com.gabia.logservice.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.gabia.logservice.domain.log.AlarmLogEntity;
import com.gabia.logservice.domain.log.AlarmMessageEntity;
import com.gabia.logservice.domain.log.LogEntity;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
public class AlarmResultResponse {
    @JsonProperty("alarmMessage")
    private AlarmMessageResponse alarmMessageResponse;

    @JsonProperty("alarmLogCount")
    private Long alarmLogResponseCount;

    @JsonProperty("alarmLogList")
    private List<AlarmLogResponse> alarmLogResponseList;

    public AlarmResultResponse(AlarmLogEntity alarmLogEntity, AlarmMessageEntity alarmMessageEntity) {
        this.alarmMessageResponse = alarmMessageEntity;
        this.resultMsg = logEntity.getResultMsg();
    }
}
