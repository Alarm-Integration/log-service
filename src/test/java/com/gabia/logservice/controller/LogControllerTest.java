package com.gabia.logservice.controller;

import com.gabia.logservice.domain.log.AlarmRequestEntity;
import com.gabia.logservice.domain.log.AlarmRequestRepository;
import com.gabia.logservice.domain.log.AlarmResultEntity;
import com.gabia.logservice.domain.log.AlarmResultRepository;
import com.gabia.logservice.dto.AlarmResultResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@AutoConfigureMockMvc
@ActiveProfiles("test")
@SpringBootTest
public class LogControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private AlarmRequestRepository alarmRequestRepository;

    @Autowired
    private AlarmResultRepository alarmResultRepository;

    private Long userId = 1L;
    private String requestId = "test_request_id";
    private String title = "test_title";
    private String content = "test_content";
    private LocalDateTime createdAt = LocalDateTime.now().withNano(0);

    private String appName = "test_app_name";
    private String logMessage = "test_log_message";
    private boolean isSuccess = true;
    private String address = "01012341234";

    private Long expectedAlarmResultEntityListSize = 1L;


    @BeforeEach
    void beforeEach() {
        alarmResultRepository.deleteAll();
        alarmRequestRepository.deleteAll();

        AlarmResultEntity alarmResultEntity = AlarmResultEntity
                .builder()
                .appName(appName)
                .logMessage(logMessage)
                .isSuccess(isSuccess)
                .address(address)
                .build();
        List<AlarmResultEntity> alarmResultEntityList = new ArrayList<>();
        alarmResultEntityList.add(alarmResultEntity);

        AlarmRequestEntity defaultAlarmRequestEntity = AlarmRequestEntity.builder()
                .userId(userId)
                .requestId(requestId)
                .title(title)
                .content(content)
                .createdAt(createdAt)
                .alarmResultEntityList(alarmResultEntityList)
                .build();
        alarmRequestRepository.save(defaultAlarmRequestEntity);
        alarmResultEntity.setAlarmRequestEntity(defaultAlarmRequestEntity);

        alarmResultRepository.save(alarmResultEntity);
    }

    @Test
    void test_getAlarmRequests_empty_success() throws Exception {
        // given
        Long differentUserId = 2L;
        String expectedSuccessMessage = "알림 발송 결과 조회용 아이디 결과 조회 완료";

        // when
        ResultActions result = mockMvc.perform(get("/log-service/alarm-requests")
                .header("user-id", differentUserId)
                .accept(APPLICATION_JSON));

        // then
        result
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value(expectedSuccessMessage));
    }

    @Test
    void test_getAlarmRequests_success() throws Exception {
        // given
        String expectedSuccessMessage = "알림 발송 결과 조회용 아이디 결과 조회 완료";

        // when
        ResultActions result = mockMvc.perform(get("/log-service/alarm-requests")
                .header("user-id", userId)
                .accept(APPLICATION_JSON));

        // then
        result
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value(expectedSuccessMessage))
                .andExpect(jsonPath("$.result.length()").value(expectedAlarmResultEntityListSize))
                .andExpect(jsonPath("$..result[0].requestId").value(requestId))
                .andExpect(jsonPath("$..result[0].title").value(title))
                .andExpect(jsonPath("$..result[0].content").value(content));
    }

    @Test
    void test_getAlarmRequests_without_user_id_header_fail() throws Exception {
        // given
        String expectedFailMessage = "MissingRequestHeaderException";
        String missingHeaderName = "user-id";
        String expectedErrorMessage = String.format("Required request header '%s' for method parameter type Long is not present", missingHeaderName);

        // when
        ResultActions result = mockMvc.perform(get("/log-service/alarm-requests")
//                .header("user_id", userId)  -> missingRequestHeader
                .accept(APPLICATION_JSON));

        System.out.println(jsonPath("$.result"));

        // then
        result
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value(expectedFailMessage))
                .andExpect(jsonPath("$.result.errorMessage").value(expectedErrorMessage))
                .andExpect(jsonPath("$.result.requiredHeaderName").value(missingHeaderName));
    }

    @Test
    void test_getAlarmRequests_with_type_mismatch_header_fail() throws Exception {
        // given
        String expectedFailMessage = "MethodArgumentTypeMismatchException";
        String typeMismatchedHeaderName = "user-id";
        String typeMismatchedUserId = "typeMismatchedUserId";
        String expectedErrorMessage = String.format("Failed to convert value of type 'java.lang.String' to required type 'java.lang.Long'; nested exception is java.lang.NumberFormatException: For input string: \"%s\"", typeMismatchedUserId);

        // when
        ResultActions result = mockMvc.perform(get("/log-service/alarm-requests")
                .header(typeMismatchedHeaderName, typeMismatchedUserId)
                .accept(APPLICATION_JSON));

        // then
        result
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value(expectedFailMessage))
                .andExpect(jsonPath("$.result.errorMessage").value(expectedErrorMessage))
                .andExpect(jsonPath("$.result.typeMismatchedHeaderName").value(typeMismatchedHeaderName));
    }

    @Test
    void test_getAlarmResults_success() throws Exception {
        // given
        String expectedSuccessMessage = "알림 발송 결과 조회 완료";

        // when
        ResultActions result = mockMvc.perform(get(String.format("/log-service/alarm-requests/%s/results", requestId))
                .header("user-id", userId)
                .accept(APPLICATION_JSON));

        // then
        result
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value(expectedSuccessMessage))
                .andExpect(jsonPath("$.result.title").value(title))
                .andExpect(jsonPath("$.result.content").value(content))
                .andExpect(jsonPath("$.result.resultCount").value(expectedAlarmResultEntityListSize))
                .andExpect(jsonPath("$.result.alarmResultList.length()").value(1))
                .andExpect(jsonPath("$.result.alarmResultList[0].isSuccess").value(isSuccess))
                .andExpect(jsonPath("$.result.alarmResultList[0].appName").value(appName))
                .andExpect(jsonPath("$.result.alarmResultList[0].logMessage").value(logMessage))
                .andExpect(jsonPath("$.result.alarmResultList[0].address").value(address));
    }

    @Test
    void test_getAlarmResults_entity_not_found_fail() throws Exception {
        // given
        Long differentUserId = 2L;
        String expectedFailMessage = "EntityNotFoundException";
        String expectedFailResult = "알림 발송 이력이 존재하지 않습니다.";

        // when
        ResultActions result = mockMvc.perform(get(String.format("/log-service/alarm-requests/%s/results", requestId))
                .header("user-id", differentUserId)
                .accept(APPLICATION_JSON));

        // then
        result
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value(expectedFailMessage))
                .andExpect(jsonPath("$.result").value(expectedFailResult));
    }

    @Test
    void test_getAlarmResults_without_user_id_header_fail() throws Exception {
        // given
        String expectedFailMessage = "MissingRequestHeaderException";
        String missingHeaderName = "user-id";
        String expectedErrorMessage = String.format("Required request header '%s' for method parameter type Long is not present", missingHeaderName);

        // when
        ResultActions result = mockMvc.perform(get(String.format("/log-service/alarm-requests/%s/results", requestId))
//                .header("user_id", userId)  -> missingRequestHeader
                .accept(APPLICATION_JSON));

        System.out.println(jsonPath("$.result"));

        // then
        result
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value(expectedFailMessage))
                .andExpect(jsonPath("$.result.errorMessage").value(expectedErrorMessage))
                .andExpect(jsonPath("$.result.requiredHeaderName").value(missingHeaderName));
    }

    @Test
    void test_getAlarmResults_with_type_mismatch_header_fail() throws Exception {
        // given
        String expectedFailMessage = "MethodArgumentTypeMismatchException";
        String typeMismatchedHeaderName = "user-id";
        String typeMismatchedUserId = "typeMismatchedUserId";
        String expectedErrorMessage = String.format("Failed to convert value of type 'java.lang.String' to required type 'java.lang.Long'; nested exception is java.lang.NumberFormatException: For input string: \"%s\"", typeMismatchedUserId);

        // when
        ResultActions result = mockMvc.perform(get(String.format("/log-service/alarm-requests/%s/results", requestId))
                .header(typeMismatchedHeaderName, typeMismatchedUserId)
                .accept(APPLICATION_JSON));

        // then
        result
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value(expectedFailMessage))
                .andExpect(jsonPath("$.result.errorMessage").value(expectedErrorMessage))
                .andExpect(jsonPath("$.result.typeMismatchedHeaderName").value(typeMismatchedHeaderName));
    }
}


