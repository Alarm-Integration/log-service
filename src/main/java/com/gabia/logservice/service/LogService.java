package com.gabia.logservice.service;

import com.gabia.logservice.domain.log.*;
import com.gabia.logservice.dto.TraceIdResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class LogService {

    private final AlarmLogRepository alarmLogRepository;
    private final AlarmMessageRepository alarmMessageRepository;

    @Transactional(readOnly = true)
    public Optional<AlarmMessageEntity> getAlarmMessage(Long userId, String traceId) {
        return alarmMessageRepository.findByUserIdAndTraceId(userId, traceId);
    }
    @Transactional(readOnly = true)
    public List<AlarmLogEntity> getAlarmLogList(Long userId, String traceId) {
        return alarmLogRepository.findByUserIdAndTraceId(userId, traceId);
    }

    @Transactional(readOnly = true)
    public List<TraceIdResponse> getAlarmTraceIdList(Long userId) {
        return alarmLogRepository.findAllByUserId(userId);
    }

}
