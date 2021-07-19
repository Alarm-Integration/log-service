package com.gabia.logservice.service;

import com.gabia.logservice.domain.log.LogEntity;
import com.gabia.logservice.domain.log.LogRepository;
import com.gabia.logservice.dto.TraceIdResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Service
public class LogService {

    private final LogRepository logRepository;

    @Transactional(readOnly = true)
    public List<LogEntity> getAlarmResultList(Long userId, String traceId) {
        return logRepository.findByUserIdAndTraceId(userId, traceId);
    }

    @Transactional(readOnly = true)
    public List<TraceIdResponse> getAlarmResultIdList(Long userId) {
        return logRepository.findAllByUserId(userId);
    }

}
