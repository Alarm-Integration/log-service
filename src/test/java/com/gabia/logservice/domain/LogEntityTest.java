package com.gabia.logservice.domain;

import com.gabia.logservice.domain.log.LogEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

public class LogEntityTest {


    @PersistenceContext
    private EntityManager em;

    @Test
    void test_log_entity_getter_method() {
        // given
        Long id = 1L;
        Long userId = 1L;
        String tracId = "test_trace_id";
        String appName = "test_app_name";
        String resultMsg = "test_result_msg";
        LocalDateTime createdAt = LocalDateTime.now();

        // when
        LogEntity logEntity = LogEntity.builder()
                .id(id)
                .userId(userId)
                .traceId(tracId)
                .appName(appName)
                .resultMsg(resultMsg)
                .createdAt(createdAt)
                .build();

        // then
        assertThat(logEntity.getId()).isEqualTo(id);
        assertThat(logEntity.getUserId()).isEqualTo(userId);
        assertThat(logEntity.getTraceId()).isEqualTo(tracId);
        assertThat(logEntity.getAppName()).isEqualTo(appName);
        assertThat(logEntity.getResultMsg()).isEqualTo(resultMsg);
        assertThat(logEntity.getCreatedAt()).isEqualTo(createdAt);
    }

    @Test
    void test_persist_data() {
        // given
        LogEntity expectedLogEntity = LogEntity.builder()
                .id(1L)
                .userId(1L)
                .traceId("test_trace_id")
                .appName("test_app_name")
                .resultMsg("test_result_msg")
                .createdAt(LocalDateTime.now())
                .build();

        // when
        em.persist(expectedLogEntity);
        em.flush();
        em.clear();
        LogEntity actualLogEntity = em.find(LogEntity.class, expectedLogEntity.getId());

        // then
        assertThat(actualLogEntity.getId()).isEqualTo(expectedLogEntity.getId());
        assertThat(actualLogEntity.getUserId()).isEqualTo(expectedLogEntity.getUserId());
        assertThat(actualLogEntity.getTraceId()).isEqualTo(expectedLogEntity.getTraceId());
        assertThat(actualLogEntity.getAppName()).isEqualTo(expectedLogEntity.getAppName());
        assertThat(actualLogEntity.getResultMsg()).isEqualTo(expectedLogEntity.getResultMsg());
        assertThat(actualLogEntity.getCreatedAt()).isEqualTo(expectedLogEntity.getCreatedAt());
    }
}
