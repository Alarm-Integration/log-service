package com.gabia.logservice.domain;

import com.gabia.logservice.domain.log.LogEntity;
import com.gabia.logservice.domain.log.LogRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@ActiveProfiles("test")
@ExtendWith(SpringExtension.class)
@Transactional
@SpringBootTest
public class LogRepositoryTest {

    @Autowired
    private LogRepository logRepository;

    @PersistenceContext
    private EntityManager em;

    @BeforeEach
    void beforeEach() {
        logRepository.deleteAll();
    }

    @Test
    void test_findByUserIdAndTraceId() {
        //given
        Long userId = 1L;
        String traceId = "test_trace_id";
        String appName = "test_app_name";
        String resultMsg = "test_result_msg";
        LocalDateTime createdAt = LocalDateTime.now();

        List<LogEntity> expectedLogEntityList = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            LogEntity logEntity = LogEntity.builder()
                    .userId(userId)
                    .traceId(traceId)
                    .resultMsg(resultMsg)
                    .appName(appName)
                    .createdAt(createdAt)
                    .build();
            em.persist(logEntity);
            expectedLogEntityList.add(logEntity);
        }

        LogEntity differentLogEntity = LogEntity.builder()
                .userId(2L)  // not same with above userId
                .traceId(traceId)  // same with above traceId
                .resultMsg(resultMsg)
                .appName(appName)
                .createdAt(createdAt)
                .build();
        em.persist(differentLogEntity);
        em.flush();
        em.clear();

        //when
        List<LogEntity> actualLogList = logRepository.findByUserIdAndTraceId(userId, traceId);

        //then
        assertThat(actualLogList.size()).isEqualTo(expectedLogEntityList.size());
        assertThat(actualLogList).containsAll(expectedLogEntityList);
        assertThat(actualLogList).doesNotContain(differentLogEntity);
    }

}