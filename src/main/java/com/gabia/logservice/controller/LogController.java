package com.gabia.logservice.controller;

import com.gabia.logservice.domain.log.LogEntity;
import com.gabia.logservice.dto.APIResponse;
import com.gabia.logservice.dto.AlarmResultResponse;
import com.gabia.logservice.service.LogService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Api(value = "LogController")
@Validated
@RequiredArgsConstructor
@RequestMapping("/log-service")
@RestController
public class LogController {

    private final LogService logService;

    @ApiOperation(value = "알림 발송 결과 조회", notes = "알림 발송 결과 조회")
    @GetMapping("/alarmResults")
    public ResponseEntity<APIResponse> GetAlarmResultLogs (
            @RequestHeader(value = "user-id") Long userId,
            @RequestHeader(value = "trace-id") String traceId
    ) {

        List<LogEntity> logEntityList = logService.getAlarmResultList(userId, traceId);

        List<AlarmResultResponse> alarmResultResponseList = new ArrayList<>();
        logEntityList.forEach((logEntity) -> {
            AlarmResultResponse response = new AlarmResultResponse(logEntity);
            alarmResultResponseList.add(response);
        });

        return ResponseEntity.ok(APIResponse.withMessage("알림 발송 결과 조회 완료", alarmResultResponseList));
    }

}
