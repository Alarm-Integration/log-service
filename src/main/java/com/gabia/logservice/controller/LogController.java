package com.gabia.logservice.controller;

import com.gabia.logservice.domain.log.AlarmRequestEntity;
import com.gabia.logservice.domain.log.AlarmResultEntity;
import com.gabia.logservice.dto.APIResponse;
import com.gabia.logservice.dto.AlarmRequestResponse;
import com.gabia.logservice.dto.AlarmResponse;
import com.gabia.logservice.dto.AlarmResultResponse;
import com.gabia.logservice.service.LogService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityNotFoundException;
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
    @GetMapping("/alarm-requests/{request-id}/results")
    public ResponseEntity<APIResponse> GetAlarmResultLogs (
            @RequestHeader(value = "user-id") Long userId,
            @PathVariable(value = "request-id") String requestId
    ) {

        AlarmRequestEntity alarmRequestEntity = logService.getAlarmRequest(userId, requestId)
                .orElseThrow(()-> new EntityNotFoundException("알림 발송 이력이 존재하지 않습니다."));

        List<AlarmResultEntity> alarmResultEntityList = alarmRequestEntity.getAlarmResultEntityList();

        List<AlarmResultResponse> alarmResultResponseList = new ArrayList<>();
        for (AlarmResultEntity alarmResultEntity : alarmResultEntityList) {
            alarmResultResponseList.add(new AlarmResultResponse(alarmResultEntity, alarmRequestEntity));
        }

        AlarmResponse alarmResponse = new AlarmResponse(alarmRequestEntity, alarmResultResponseList);
        return ResponseEntity.ok(APIResponse.withMessage("알림 발송 결과 조회 완료", alarmResponse));
    }

    @ApiOperation(value = "사용자의 알림 발송 조회 아이디 목록 조회", notes = "알림 발송 조회 아이디 목록 조회")
    @GetMapping("/alarm-requests")
    public ResponseEntity<APIResponse> GetAlarmResultIdList(@RequestHeader(value = "user-id") Long userId) {

        List<AlarmRequestResponse> alarmRequestResponseList = new ArrayList<>();

        List<AlarmRequestEntity> alarmRequestEntityList = logService.getAlarmRequestList(userId);
        for (AlarmRequestEntity alarmRequestEntity : alarmRequestEntityList) {
            alarmRequestResponseList.add(new AlarmRequestResponse(alarmRequestEntity.getRequestId(), alarmRequestEntity.getCreatedAt()));
        }
        return ResponseEntity.ok(APIResponse.withMessage("알림 발송 결과 조회용 아이디 결과 조회 완료", alarmRequestResponseList));
    }

}