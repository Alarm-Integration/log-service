package com.gabia.logservice.domain.log;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class AlarmRequestEntityTest {

    @Autowired
    private AlarmRequestRepository alarmRequestRepository;

    @PersistenceContext
    private EntityManager em;

    @BeforeEach
    void beforeEach() {
        alarmRequestRepository.deleteAll();
    }

    @Test
    void test_getter_method() {
        // given
        Long userId = 1L;
        String requestId = "test_request_id";
        String title = "test_title";
        String content = "test_content";

        // when
        AlarmRequestEntity alarmRequestEntity = AlarmRequestEntity.builder()
                .requestId(requestId)
                .userId(userId)
                .title(title)
                .content(content)
                .build();


        // then
        assertThat(alarmRequestEntity.getUserId()).isEqualTo(userId);
        assertThat(alarmRequestEntity.getRequestId()).isEqualTo(requestId);
        assertThat(alarmRequestEntity.getTitle()).isEqualTo(title);
        assertThat(alarmRequestEntity.getContent()).isEqualTo(content);
    }

    @Test
    void test_persist_data() {
        // given
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

        // when
        em.persist(expectedAlarmRequestEntity);
        em.flush();
        em.clear();
        AlarmRequestEntity actualAlarmRequestEntity = em.find(AlarmRequestEntity.class, expectedAlarmRequestEntity.getRequestId());

        // then
        assertThat(actualAlarmRequestEntity).isEqualTo(expectedAlarmRequestEntity);
    }

}
