package com.gabia.logservice.dto;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class AlarmResultResponse {
    private String appName;
    private String resultMsg;
    private LocalDateTime createdAt;
}
