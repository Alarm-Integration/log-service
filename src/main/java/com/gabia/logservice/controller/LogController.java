package com.gabia.logservice.controller;

import com.gabia.logservice.dto.APIResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.Size;

@Validated
@RequiredArgsConstructor
@RestController
public class LogController {

    private final MessageSource messageSource;

    @GetMapping
    public ResponseEntity<APIResponse> GetAlarmResultLogs (
            @RequestHeader(value = "user_id") @Size(min = 1) String userId,
            @RequestHeader(value = "trace_id") @Size(min = 1) String traceId
    ) {

        System.out.println("userId ::: " + userId);
        System.out.println("traceId ::: " + traceId);
        return ResponseEntity.ok(APIResponse.withMessage("알림 발송 결과 조회 완료", null));
    }

}
