package com.gabia.logservice.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.gabia.logservice.domain.log.AlarmRequestEntity;
import lombok.Getter;

import java.util.List;

@Getter
public class AlarmResponse {

    @JsonProperty("title")
    private String title;

    @JsonProperty("content")
    private String content;

    @JsonProperty("resultCount")
    private int resultCount;

    @JsonProperty("alarmResultList")
    private List<AlarmResultResponse> alarmResultResponseList;

    public AlarmResponse(AlarmRequestEntity alarmRequestEntity, List<AlarmResultResponse> alarmResultResponseList) {
        this.title = alarmRequestEntity.getTitle();
        this.content = alarmRequestEntity.getContent();
        this.resultCount = alarmResultResponseList.size();
        this.alarmResultResponseList = alarmResultResponseList;
    }

}