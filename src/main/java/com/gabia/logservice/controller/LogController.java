package com.gabia.logservice.controller;

import com.gabia.logservice.dto.APIResponse;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@Validated
@RestController
public class LogController {

    @GetMapping
    public APIResponse GetAlarmResultLogs() {

    }

}
