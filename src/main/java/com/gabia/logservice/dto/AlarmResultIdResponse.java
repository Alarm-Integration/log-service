package com.gabia.logservice.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

@Getter
@RequiredArgsConstructor
public class AlarmResultIdResponse {
    @JsonProperty("alarm_result_id")
    private final String alarmResultId;

    @JsonProperty("created_at")
    private final LocalDateTime createdAt;
}
