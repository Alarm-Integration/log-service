package com.gabia.logservice.service;

import com.gabia.logservice.domain.log.LogEntity;
import com.gabia.logservice.domain.log.LogRepository;
import com.gabia.logservice.dto.AlarmResultResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class LogService {

    private final LogRepository logRepository;

    @Transactional(readOnly = true)
    public List<AlarmResultResponse> getAlarmResultList(Long userId, String traceId) {

        List<LogEntity> logEntityList = logRepository.findByUserIdAndTraceId(userId, traceId);

        List<AlarmResultResponse> alarmResultResponseList = new ArrayList<>();
        logEntityList.forEach((logEntity) -> {
            AlarmResultResponse response = new AlarmResultResponse(logEntity);
            alarmResultResponseList.add(response);
        });

        return alarmResultResponseList;
    }

}
