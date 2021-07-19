package com.gabia.logservice.domain.log;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface AlarmMessageRepository extends JpaRepository<AlarmMessageEntity, Long> {
    Optional<AlarmMessageEntity> findByUserIdAndTraceId(Long userId, String traceId);
}
