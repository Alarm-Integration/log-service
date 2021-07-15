package com.gabia.logservice.dto;


import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class APIResponseTest {
    @Test
    public void test_getter_method() {
        //given
        String message = "response message";
        Object result = new Object();

        //when
        APIResponse apiResponse = APIResponse.withMessage(message, result);

        //then
        assertThat(apiResponse.getMessage()).isEqualTo(message);
        assertThat(apiResponse.getResult()).isEqualTo(result);
    }

}
