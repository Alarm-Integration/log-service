package com.gabia.logservice.domain.log;

import com.gabia.logservice.dto.AlarmResultResponse;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AlarmResultRepository extends JpaRepository<AlarmResultEntity, Long> {
}
