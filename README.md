## Log Service

> 알림 통합 서비스에서 알림을 발송한 사용자가 그 결과를 알고 싶을 때 사용하는 api server

### APIs

1. GET /log-service/alarm-requests/{request-id}/results
    - 알림 발송 결과 조회 api
    - request header
        - "user-id" : "1"
    - request path variable      
        - "request-id" : "989e7dc5-6150-4764-8a34-cd929631018e"
    - response
        1. 200 OK
             ```json
            {
                "message": "알림 발송 결과 조회 완료",
                "result": {
                    "title": "::: 가용성 테스트 :::",
                    "content": "hello-world",
                    "resultCount": 3,
                    "alarmResultList": [
                        {
                            "success": true,
                            "appName": "email",
                            "logMessage": "메일 발송 성공",
                            "address": "kmk@gabia.com",
                            "isSuccess": true,
                        "createdAt": "2021-07-21T01:29:14"
                        },
                        {
                            "success": true,
                            "appName": "sms",
                            "logMessage": "정상 접수(이통사로 접수 예정) ",
                            "address": "01092988726",
                            "isSuccess": true,
                            "createdAt": "2021-07-21T01:29:14"
                        },
                        {
                            "success": true,
                            "appName": "slack",
                            "logMessage": "슬랙 발송 성공",
                            "address": "C023WJKCPUM",
                            "isSuccess": true,
                            "createdAt": "2021-07-21T01:29:14"
                        }
                    ]
                }
            }
             ```
        2. 400 BAD REQUEST - request header에 필수 key값이 빠졌을 때 (e.g. user-id이 빠졌을 때)
            ```json
             {
               "message": "MissingRequestHeaderException",
               "result": {
                  "error_message": "Required request header 'user-id' for method parameter type Long is not present",
                  "required_header_name": "user-id"
               }
             }
             ```
        3. 400 BAD REQUEST - request header에 값 타입이 틀렸을 때 (e.g. user-id=test)
            ```json
             {
                 "message": "MethodArgumentTypeMismatch",
                 "result": {
                    "error_message": "Failed to convert value of type 'java.lang.String' to required type 'java.lang.Long'; nested exception is java.lang.NumberFormatException: For input string: \"sdf\"",
                    "type_mismatched_header_name": "user-id"
                 }
             }
             ```
2. GET /log-service/alarm-requests
    - 사용자의 알림 발송 조회 아이디 목록 조회 api
    - request header
        - "user-id" : "1"
    - response
        1. 200 OK
             ```json
             {
                "message": "알림 발송 결과 조회용 아이디 결과 조회 완료",
                "result": [
                    {
                        "requestId": "35fa4b67-9afd-44eb-a30b-12f84bd1803f",
                        "title": "::: 가용성 테스트 :::",
                        "content": "hello-world",
                        "createdAt": "2021-07-21T01:28:51"
                    },
                    {
                        "requestId": "47ac887e-1f7c-4bd4-bd3f-a891ca9c2f11",
                        "title": "::: 가용성 테스트 :::",
                        "content": "hello-world",
                        "createdAt": "2021-07-21T01:29:00"
                    },
                    {
                        "requestId": "541e42a5-9ba9-4c46-b4ca-b15137b0498d",
                        "title": "::: 가용성 테스트 :::",
                        "content": "hello-world",
                        "createdAt": "2021-07-21T01:28:57"
                    },
                    {
                        "requestId": "6efaddf4-e423-498e-8a21-91ff50c7db3f",
                        "title": "::: 정상 요청 :::",
                        "content": "hello-world",
                        "createdAt": "2021-07-21T01:24:32"
                    }
                ]
             }
             ```
        2. 400 BAD REQUEST - request header에 필수 key값이 빠졌을 때 (e.g. user-id이 빠졌을 때)
            ```json
             {
               "message": "MissingRequestHeaderException",
               "result": {
                  "error_message": "Required request header 'user-id' for method parameter type Long is not present",
                  "required_header_name": "user-id"
               }
             }
             ```
        3. 400 BAD REQUEST - request header에 값 타입이 틀렸을 때 (e.g. user-id=test)
            ```json
             {
                 "message": "MethodArgumentTypeMismatch",
                 "result": {
                    "error_message": "Failed to convert value of type 'java.lang.String' to required type 'java.lang.Long'; nested exception is java.lang.NumberFormatException: For input string: \"sdf\"",
                    "type_mismatched_header_name": "user-id"
                 }
             }
             ```
           
## JPA QUERY
```java
package com.gabia.logservice.domain.log;

import com.gabia.logservice.dto.AlarmResultIdResponse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface AlarmRequestRepository extends JpaRepository<AlarmRequestEntity, Long> {

    // 사용자가 특정 알림 발송 요청에 대해서 조회
    Optional<AlarmRequestEntity> findByUserIdAndRequestId(Long userId, String requestId);

    // 사용자가 알림 발송 요청한 목록 조회
    @Query("SELECT ar FROM alarm_requests AS ar WHERE ar.userId = :userId GROUP BY ar.requestId")
    List<AlarmRequestEntity> findAllByUserIdAndGroupByRequestId(@Param("userId") Long userId);

}
```