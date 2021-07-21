package com.gabia.logservice.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.gabia.logservice.domain.log.AlarmRequestEntity;
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

    @JsonProperty("title")
    private final String title;

    @JsonProperty("content")
    private final String content;

    @JsonProperty("createdAt")
    private final LocalDateTime createdAt;

    @Builder
    public AlarmRequestResponse(AlarmRequestEntity alarmRequestEntity) {
        this.requestId = alarmRequestEntity.getRequestId();
        this.title = alarmRequestEntity.getTitle();
        this.content = alarmRequestEntity.getContent();
        this.createdAt = alarmRequestEntity.getCreatedAt();
    }

}
