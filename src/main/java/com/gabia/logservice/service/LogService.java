package com.gabia.logservice.service;

import com.gabia.logservice.domain.log.LogRepository;
import com.gabia.logservice.dto.AlarmResultResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Service
public class LogService {

    private final LogRepository logRepository;

    @Transactional(readOnly = true)
    public List<AlarmResultResponse> getAlarmResultList(String userIdStr, String traceId) {
        Long userId = Long.parseLong(userIdStr);

//        logRepository.findby
    }

}
