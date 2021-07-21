package com.gabia.logservice.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Getter;

@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class APIResponse {
    private final String message;
    private final Object result;

    public static APIResponse withMessage(String message, Object result){
        return APIResponse.builder()
                .message(message)
                .result(result)
                .build();
    }

    @Builder
    public APIResponse(String message, Object result) {
        this.message = message;
        this.result = result;
    }

}
