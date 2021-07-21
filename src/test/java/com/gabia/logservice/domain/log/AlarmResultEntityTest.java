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
public class AlarmResultEntityTest {

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
        boolean isSuccess = true;
        String appName = "test_app_name";
        String logMessage = "test_log_message";
        String address = "test_address";

        // when
        AlarmResultEntity alarmResultEntity = AlarmResultEntity.builder()
                .isSuccess(isSuccess)
                .appName(appName)
                .logMessage(logMessage)
                .address(address)
                .build();

        // then
        assertThat(alarmResultEntity.isSuccess()).isEqualTo(isSuccess);
        assertThat(alarmResultEntity.getAppName()).isEqualTo(appName);
        assertThat(alarmResultEntity.getLogMessage()).isEqualTo(logMessage);
        assertThat(alarmResultEntity.getAddress()).isEqualTo(address);
    }

    @Test
    void test_persist_data() {
        // given
        Long userId = 1L;
        boolean isSuccess = true;
        String appName = "test_app_name";
        String logMessage = "test_log_message";
        String address = "test_address";

        AlarmResultEntity expectedAlarmResultEntity = AlarmResultEntity.builder()
                .isSuccess(isSuccess)
                .appName(appName)
                .logMessage(logMessage)
                .address(address)
                .build();
        AlarmResultEntity expectedNotExistingAlarmResultEntity = AlarmResultEntity.builder()
                .isSuccess(isSuccess)
                .appName(appName)
                .logMessage(logMessage)
                .address(address)
                .build();

        // when
        em.persist(expectedAlarmResultEntity);
        em.flush();
        em.clear();
        AlarmResultEntity actualExistingAlarmResultEntity = em.find(AlarmResultEntity.class, expectedAlarmResultEntity.getId());
        AlarmResultEntity actualNotExistingAlarmResultEntity = em.find(AlarmResultEntity.class, 2L);

        // then
        assertThat(actualExistingAlarmResultEntity).isEqualTo(expectedAlarmResultEntity);
        assertThat(actualNotExistingAlarmResultEntity).isNull();
    }
}

