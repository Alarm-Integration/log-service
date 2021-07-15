package com.gabia.logservice.domain;

import com.gabia.logservice.domain.log.LogEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

@Transactional
@SpringBootTest
public class LogEntityTest {


    @PersistenceContext
    private EntityManager em;

    @Test
    void test_getter_method() {
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
        LogEntity expectedExistingLogEntity = LogEntity.builder()
                .userId(1L)
                .traceId("test_trace_id")
                .appName("test_app_name")
                .resultMsg("test_result_msg")
                .createdAt(LocalDateTime.now())
                .build();
        LogEntity expectedNotExistingLogEntity = LogEntity.builder()
                .id(2L)
                .userId(2L)
                .traceId("test_trace_id2")
                .appName("test_app_name2")
                .resultMsg("test_result_msg2")
                .createdAt(LocalDateTime.now())
                .build();

        // when
        em.persist(expectedExistingLogEntity);
        em.flush();
        em.clear();
        LogEntity actualExistingLogEntity = em.find(LogEntity.class, expectedExistingLogEntity.getId());
        LogEntity actualNotExistingLogEntity = em.find(LogEntity.class, 2L);

        // then
        assertThat(actualExistingLogEntity).isEqualTo(expectedExistingLogEntity);
        assertThat(actualNotExistingLogEntity).isNull();
    }
}
