package com.gabia.logservice.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

@Getter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class AlarmRequestResponse {

    @JsonProperty("requestId")
    @EqualsAndHashCode.Include
    private final String requestId;

    @JsonProperty("createdAt")
    private final LocalDateTime createdAt;

    @Builder
    public AlarmRequestResponse(String requestId, LocalDateTime createdAt) {
        this.requestId = requestId;
        this.createdAt = createdAt;
    }

}
