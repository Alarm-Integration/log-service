package com.gabia.logservice.domain.log;

import com.gabia.logservice.dto.AlarmRequestResponse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface AlarmRequestRepository extends JpaRepository<AlarmRequestEntity, Long> {

    Optional<AlarmRequestEntity> findByUserIdAndRequestId(Long userId, String requestId);

    @Query("SELECT ar FROM alarm_requests AS ar WHERE ar.userId = :userId GROUP BY ar.requestId")
    List<AlarmRequestEntity> findAllByUserIdAndGroupByRequestId(@Param("userId") Long userId);

}
