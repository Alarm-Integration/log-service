package com.gabia.logservice.service;

import com.gabia.logservice.LogServiceApplication;
import com.gabia.logservice.domain.log.LogEntity;
import com.gabia.logservice.domain.log.LogRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@ActiveProfiles("test")
@Transactional
@DataJpaTest
public class LogServiceTest {

    @Autowired
    private LogService logService;

    @Autowired
    private LogRepository logRepository;

    @PersistenceContext
    private EntityManager em;

    @BeforeEach
    void beforeEach() {
        logRepository.deleteAll();
    }

    @Test
    void test_getAlarmResultList() {
        //given
        Long userId = 1L;
        String traceId = "test_trace_id";
        List<LogEntity> expectedLogEntityList = new ArrayList<>();

        for (int i = 0; i < 3; i++) {
            LogEntity logEntity = LogEntity.builder()
                    .userId(userId)
                    .traceId(traceId)
                    .resultMsg("test_result_msg" + i)
                    .appName("test_app_name" + i)
                    .createdAt(LocalDateTime.now())
                    .build();
            em.persist(logEntity);
            expectedLogEntityList.add(logEntity);
        }

        LogEntity differentLogEntity = LogEntity.builder()
                .userId(2L)  // not same with above userId
                .traceId(traceId)  // same with above traceId
                .resultMsg("test_result_msg")
                .appName("test_app_name")
                .createdAt(LocalDateTime.now())
                .build();
        em.persist(differentLogEntity);
        em.flush();
        em.clear();

        //when
        List<LogEntity> actualLogEntityList = logService.getAlarmResultList(userId, traceId);

        //then
        assertThat(actualLogEntityList.size()).isEqualTo(expectedLogEntityList.size());
        assertThat(actualLogEntityList).containsAll(expectedLogEntityList);
        assertThat(actualLogEntityList).doesNotContain(differentLogEntity);
    }

}
