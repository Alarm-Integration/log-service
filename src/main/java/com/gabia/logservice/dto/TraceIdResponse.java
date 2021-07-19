package com.gabia.logservice.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

@Getter
@RequiredArgsConstructor
public class TraceIdResponse {
    @JsonProperty("traceId")
    private final String traceId;

    @JsonProperty("createdAt")
    private final LocalDateTime createdAt;
}
