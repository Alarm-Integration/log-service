package com.gabia.logservice.domain.log;

import com.gabia.logservice.dto.AlarmRequestResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@ActiveProfiles("test")
@ExtendWith(SpringExtension.class)
@Transactional
@DataJpaTest
public class AlarmRequestRepositoryTest {

    @Autowired
    private AlarmRequestRepository alarmRequestRepository;

    @PersistenceContext
    private EntityManager em;

    @BeforeEach
    void beforeEach() {
        alarmRequestRepository.deleteAll();
    }

    @Test
    void test_findByUserIdAndRequestId() {
        //given
        Long userId = 1L;
        String requestId = "test_request_id";
        String title = "test_title";
        String content = "test_content";
        LocalDateTime createdAt = LocalDateTime.now();

        AlarmRequestEntity expectedAlarmRequestEntity = AlarmRequestEntity.builder()
                .requestId(requestId)
                .userId(userId)
                .title(title)
                .content(content)
                .createdAt(createdAt)
                .build();
        em.persist(expectedAlarmRequestEntity);
        em.flush();
        em.clear();

        //when
        Optional<AlarmRequestEntity> actualAlarmRequestEntity = alarmRequestRepository.findByUserIdAndRequestId(userId, requestId);
        Optional<AlarmRequestEntity> emptyAlarmRequestEntity = alarmRequestRepository.findByUserIdAndRequestId(2L, requestId);

        //then
        assertThat(actualAlarmRequestEntity).isNotEmpty();
        actualAlarmRequestEntity.ifPresent(presentAlarmRequestEntity -> {
            assertThat(presentAlarmRequestEntity).isEqualTo(expectedAlarmRequestEntity);
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
        long expectedRequestResponseListSize = 3L;
        LocalDateTime createdAt = LocalDateTime.now();

        AlarmResultEntity alarmResultEntity = AlarmResultEntity
                .builder()
                .appName("test")
                .logMessage("test_message")
                .isSuccess(true)
                .address("01012341234")
                .build();
        List<AlarmResultEntity> alarmResultEntityList = new ArrayList<>();
        alarmResultEntityList.add(alarmResultEntity);
        em.persist(alarmResultEntity);

        for (int i = 0; i < expectedRequestResponseListSize; i++) {
            AlarmRequestEntity alarmRequestEntity = AlarmRequestEntity
                    .builder()
                    .userId(userId+i)
                    .requestId(requestId+i)
                    .title(title)
                    .content(content)
                    .createdAt(createdAt)
                    .alarmResultEntityList(alarmResultEntityList)
                    .build();
            em.persist(alarmRequestEntity);
        }

        AlarmRequestEntity differentAlarmRequestEntity = AlarmRequestEntity
                .builder()
                .userId(2L)  // different from above userId
                .requestId(requestId + "test")  // different from above requestId
                .title(title)
                .content(content)
                .createdAt(createdAt)
                .alarmResultEntityList(alarmResultEntityList)
                .build();
        em.persist(differentAlarmRequestEntity);
        em.flush();
        em.clear();

        // when
        List<AlarmRequestEntity> alarmRequestEntityList = alarmRequestRepository.findAllByUserIdAndGroupByRequestId(userId);

        // then
        assertThat(alarmRequestEntityList.size()).isEqualTo(1L);
        assertThat(alarmRequestEntityList).doesNotContain(differentAlarmRequestEntity);
    }

}
