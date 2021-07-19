package com.gabia.logservice.domain.log;

import com.gabia.logservice.dto.TraceIdResponse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface AlarmLogRepository extends JpaRepository<AlarmLogEntity, Long> {

    List<AlarmLogEntity> findByUserIdAndTraceId(Long userId, String traceId);

    @Query("SELECT new com.gabia.logservice.dto.TraceIdResponse(l.traceId, MIN(l.createdAt)) FROM alarm_logs l WHERE l.userId = :userId GROUP BY l.traceId")
    List<TraceIdResponse> findAllByUserId(@Param("userId") Long userId);

}
