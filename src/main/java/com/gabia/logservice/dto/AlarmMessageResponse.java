package com.gabia.logservice.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

@Getter
class AlarmMessageResponse {
    @JsonProperty("title")
    private String title;

    @JsonProperty("content")
    private String content;
}