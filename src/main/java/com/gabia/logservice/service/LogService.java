package com.gabia.logservice.service;

import com.gabia.logservice.domain.log.*;
import com.gabia.logservice.dto.AlarmRequestResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class LogService {

    private final AlarmRequestRepository alarmRequestRepository;

    @Transactional(readOnly = true)
    public Optional<AlarmRequestEntity> getAlarmRequest(Long userId, String requestId) {
        return alarmRequestRepository.findByUserIdAndRequestId(userId, requestId);
    }

    // 후보 목록 조회
    @Transactional(readOnly = true)
    public List<AlarmRequestEntity> getAlarmRequestList(Long userId) {
        return alarmRequestRepository.findAllByUserIdAndGroupByRequestId(userId);
    }

}
