package com.gabia.logservice.controller;

import com.gabia.logservice.dto.APIResponse;
import com.gabia.logservice.dto.AlarmResultResponse;
import com.gabia.logservice.service.LogService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.Size;
import java.util.List;

@Validated
@RequiredArgsConstructor
@RestController
public class LogController {

    private final LogService logService;

    @GetMapping
    public ResponseEntity<APIResponse> GetAlarmResultLogs (
            @RequestHeader(value = "user_id") Long userId,
            @RequestHeader(value = "trace_id") String traceId
    ) {

        List<AlarmResultResponse> alarmResultResponseList = logService.getAlarmResultList(userId, traceId);
        return ResponseEntity.ok(APIResponse.withMessage("알림 발송 결과 조회 완료", alarmResultResponseList));
    }

}
