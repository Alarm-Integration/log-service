package com.gabia.logservice.domain.log;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LogRepository extends JpaRepository<LogEntity, Long> {
    List<LogEntity> findByUserIdAndTraceId(Long userId, String traceId);
}
