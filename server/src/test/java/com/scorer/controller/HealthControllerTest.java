package com.scorer.controller;

import com.scorer.BaseTest;
import org.springframework.http.MediaType;
import org.testng.annotations.Test;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class HealthControllerTest extends BaseTest {

    @Test
    public void health_dbIsUp_returnsOk() throws Exception {
        mockMvc.perform(post("/health")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("ok"));
    }

    @Test
    public void healthOK_returnsOk() throws Exception {
        mockMvc.perform(post("/healthOK")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("ok"));
    }

}
