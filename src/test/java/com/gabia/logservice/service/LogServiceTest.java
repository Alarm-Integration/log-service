package com.gabia.logservice.service;

import com.gabia.logservice.domain.log.AlarmRequestEntity;
import com.gabia.logservice.domain.log.AlarmRequestRepository;
import com.gabia.logservice.domain.log.AlarmResultEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@Transactional
@ActiveProfiles("test")
@SpringBootTest
public class LogServiceTest {

    @Autowired
    private AlarmRequestRepository alarmRequestRepository;

    @Autowired
    private LogService logService;

    @PersistenceContext
    private EntityManager em;

    @BeforeEach
    void beforeEach() {
        alarmRequestRepository.deleteAll();
    }

    @Test
    void test_getAlarmRequest() {
        //given
        Long userId = 1L;
        String requestId = "test_request_id";
        String title = "test_title";
        String content = "test_content";
        LocalDateTime createdAt = LocalDateTime.now();
        AlarmRequestEntity expectedAlarmRequestEntity = AlarmRequestEntity
                .builder()
                .userId(userId)
                .requestId(requestId)
                .title(title)
                .content(content)
                .createdAt(createdAt)
                .build();

        em.persist(expectedAlarmRequestEntity);
        em.flush();
        em.clear();

        //when
        Optional<AlarmRequestEntity> actualAlarmRequestEntity = logService.getAlarmRequest(userId, requestId);
        Optional<AlarmRequestEntity> emptyAlarmRequestEntity = logService.getAlarmRequest(2L, requestId);

        //then
        assertThat(actualAlarmRequestEntity).isNotEmpty();
        actualAlarmRequestEntity.ifPresent(presentAlarmRequestEntity -> {
            assertThat(presentAlarmRequestEntity.getUserId()).isEqualTo(expectedAlarmRequestEntity.getUserId());
            assertThat(presentAlarmRequestEntity.getRequestId()).isEqualTo(expectedAlarmRequestEntity.getRequestId());
            assertThat(presentAlarmRequestEntity.getTitle()).isEqualTo(expectedAlarmRequestEntity.getTitle());
            assertThat(presentAlarmRequestEntity.getContent()).isEqualTo(expectedAlarmRequestEntity.getContent());
        });
        assertThat(emptyAlarmRequestEntity).isEmpty();
    }

    @Test
    void test_getAlarmRequestList() {
        //given
        Long userId = 1L;
        String requestId = "test_request_id";
        String title = "test_title";
        String content = "test_content";
        LocalDateTime createdAt = LocalDateTime.now();
        long expectedRequestResponseListSize = 3L;

        for (int i = 0; i < expectedRequestResponseListSize; i++) {
            AlarmRequestEntity alarmRequestEntity = AlarmRequestEntity
                    .builder()
                    .userId(userId)
                    .requestId(requestId + i)
                    .title(title + i)
                    .content(content + i)
                    .createdAt(createdAt)
                    .build();
            em.persist(alarmRequestEntity);
        }

        AlarmRequestEntity differentAlarmRequestEntity = AlarmRequestEntity
                .builder()
                .userId(2L)  // different from above userId
                .requestId(requestId)
                .title(title)
                .content(content)
                .createdAt(LocalDateTime.now())
                .build();
        em.persist(differentAlarmRequestEntity);

        em.flush();
        em.clear();

        // when
        List<AlarmRequestEntity> alarmRequestEntityList = logService.getAlarmRequestList(userId);

        // then
        assertThat(alarmRequestEntityList.size()).isEqualTo(expectedRequestResponseListSize);
        assertThat(alarmRequestEntityList).doesNotContain(differentAlarmRequestEntity);
    }
}
