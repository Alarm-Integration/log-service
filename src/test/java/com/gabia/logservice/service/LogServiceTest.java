package com.gabia.logservice.service;

import com.gabia.logservice.domain.log.LogEntity;
import com.gabia.logservice.dto.AlarmResultResponse;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@Transactional
@SpringBootTest
public class LogServiceTest {

    @Autowired
    private LogService logService;

    @PersistenceContext
    private EntityManager em;

    @Test
    void test_getAlarmResultList() {
        //given
        Long userId = 1L;
        String traceId = "test_trace_id";

        for (int i = 0; i < 3; i++) {
            LogEntity logEntity = LogEntity.builder()
                    .userId(userId)
                    .traceId(traceId)
                    .resultMsg("test_result_msg" + i)
                    .appName("test_app_name" + i)
                    .createdAt(LocalDateTime.now())
                    .build();
            em.persist(logEntity);
        }

        LogEntity logEntity = LogEntity.builder()
                .userId(2L)  // not same with above userId
                .traceId(traceId)  // same with above traceId
                .resultMsg("test_result_msg")
                .appName("test_app_name")
                .createdAt(LocalDateTime.now())
                .build();
        em.persist(logEntity);
        em.flush();
        em.clear();

        List<AlarmResultResponse> expectedAlarmResultResponseList = new ArrayList<>();
        for (int i = 1; i < 5; i++) {
            expectedAlarmResultResponseList.add(new AlarmResultResponse(em.find(LogEntity.class, (long) i)));
        }

        //when
        List<AlarmResultResponse> actualAlarmResultResponseList = logService.getAlarmResultList(userId, traceId);

        //then
        assertThat(actualAlarmResultResponseList.size()).isLessThan(expectedAlarmResultResponseList.size());
        assertThat(actualAlarmResultResponseList).doesNotContain(expectedAlarmResultResponseList.get(3));

        for (int i = 0; i < 3; i++) {
            AlarmResultResponse expectedAlarmResultResponse = expectedAlarmResultResponseList.get(i);
            AlarmResultResponse actualAlarmResultResponse = actualAlarmResultResponseList.get(i);

            assertThat(actualAlarmResultResponse.getAppName()).isEqualTo(expectedAlarmResultResponse.getAppName());
            assertThat(actualAlarmResultResponse.getResultMsg()).isEqualTo(expectedAlarmResultResponse.getResultMsg());
            assertThat(actualAlarmResultResponse.getCreatedAt()).isEqualTo(expectedAlarmResultResponse.getCreatedAt());
        }
    }

}
