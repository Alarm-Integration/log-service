package com.gabia.logservice.controller;

import com.gabia.logservice.LogServiceApplication;
import com.gabia.logservice.domain.log.LogEntity;
import com.gabia.logservice.domain.log.LogRepository;
import com.gabia.logservice.dto.AlarmResultResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@ActiveProfiles("test")
@AutoConfigureMockMvc
@SpringBootTest
public class LogControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private LogRepository logRepository;
    private Long userId = 1L;
    private String traceId = "test_trace_id";
    private String appName = "test_app_name";
    private String resultMsg = "test_result_msg";
    private LocalDateTime createdAt = LocalDateTime.now().withNano(0);

    @BeforeEach
    void beforeEach() {
        logRepository.deleteAll();

        LogEntity defaultLogEntity = LogEntity.builder()
                .userId(userId)
                .traceId(traceId)
                .appName(appName)
                .resultMsg(resultMsg)
                .createdAt(createdAt)
                .build();

        logRepository.save(defaultLogEntity);
    }

    @Test
    void test_getAlarmResultLogs_empty_success() throws Exception {
        // given
        Long differentUserId = 2L;
        String expectedSuccessMessage = "알림 발송 결과 조회 완료";

        // when
        ResultActions result = mockMvc.perform(get("/logs")
                .header("user_id", differentUserId)
                .header("trace_id", traceId)
                .accept(APPLICATION_JSON));

        // then
        result
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value(expectedSuccessMessage))
                .andExpect(jsonPath("$.result").isEmpty());
    }

    @Test
    void test_getAlarmResultLogs_success() throws Exception {
        // given
        String expectedSuccessMessage = "알림 발송 결과 조회 완료";
        List<AlarmResultResponse> expectedAlarmResultResponseList = new ArrayList<>();
        expectedAlarmResultResponseList.add(new AlarmResultResponse(LogEntity.builder()
                .userId(userId)
                .traceId(traceId)
                .appName(appName)
                .resultMsg(resultMsg)
                .createdAt(createdAt)
                .build()));

        // when
        ResultActions result = mockMvc.perform(get("/logs")
                .header("user_id", userId)
                .header("trace_id", traceId)
                .accept(APPLICATION_JSON));

        // then
        result
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value(expectedSuccessMessage))
                .andExpect(jsonPath("$.result.length()").value(expectedAlarmResultResponseList.size()))
                .andExpect(jsonPath("$..result[0].app_name").value(appName))
                .andExpect(jsonPath("$..result[0].result_msg").value(resultMsg))
                .andExpect(jsonPath("$..result[0].created_at").value(createdAt.toString()));
    }

    @Test
    void test_getAlarmResultLogs_without_user_id_header_fail() throws Exception {
        // given
        String expectedFailMessage = "MissingRequestHeaderException";
        String missingHeaderName = "user_id";
        String expectedErrorMessage = String.format("Required request header '%s' for method parameter type Long is not present", missingHeaderName);

        // when
        ResultActions result = mockMvc.perform(get("/logs")
//                .header("user_id", userId)
                .header("trace_id", traceId)
                .accept(APPLICATION_JSON));

        System.out.println(jsonPath("$.result"));

        // then
        result
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value(expectedFailMessage))
                .andExpect(jsonPath("$.result.error_message").value(expectedErrorMessage))
                .andExpect(jsonPath("$.result.required_header_name").value(missingHeaderName));
    }

    @Test
    void test_getAlarmResultLogs_without_trace_id_header_fail() throws Exception {
        // given
        String expectedFailMessage = "MissingRequestHeaderException";
        String missingHeaderName = "trace_id";
        String expectedErrorMessage = String.format("Required request header '%s' for method parameter type String is not present", missingHeaderName);

        // when
        ResultActions result = mockMvc.perform(get("/logs")
//                .header("trace_id", traceId)
                .header("user_id", userId)
                .accept(APPLICATION_JSON));

        System.out.println(jsonPath("$.result"));

        // then
        result
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value(expectedFailMessage))
                .andExpect(jsonPath("$.result.error_message").value(expectedErrorMessage))
                .andExpect(jsonPath("$.result.required_header_name").value(missingHeaderName));
    }

    @Test
    void test_getAlarmResultLogs_with_type_mismatch_header_fail() throws Exception {
        // given
        String expectedFailMessage = "MethodArgumentTypeMismatch";
        String typeMismatchedHeaderName = "user_id";
        String typeMismatchedUserId = "typeMismatchedUserId";
        String expectedErrorMessage = String.format("Failed to convert value of type 'java.lang.String' to required type 'java.lang.Long'; nested exception is java.lang.NumberFormatException: For input string: \"%s\"", typeMismatchedUserId);

        // when
        ResultActions result = mockMvc.perform(get("/logs")
                .header(typeMismatchedHeaderName, typeMismatchedUserId)
                .header("trace_id", traceId)
                .accept(APPLICATION_JSON));

        // then
        result
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value(expectedFailMessage))
                .andExpect(jsonPath("$.result.error_message").value(expectedErrorMessage))
                .andExpect(jsonPath("$.result.type_mismatched_header_name").value(typeMismatchedHeaderName));
    }
}
