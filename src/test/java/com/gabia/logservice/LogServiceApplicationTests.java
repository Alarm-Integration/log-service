package com.gabia.logservice;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@ActiveProfiles("test")
@SpringBootTest(classes = LogServiceApplication.class)
class LogServiceApplicationTests {

    @Test
    void contextLoads() {
    }

}
