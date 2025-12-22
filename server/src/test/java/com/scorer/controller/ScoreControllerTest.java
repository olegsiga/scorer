package com.scorer.controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.scorer.BaseTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;
import org.testng.annotations.Test;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotNull;

public class ScoreControllerTest extends BaseTest {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Test
    public void submit_validLongJump_returnsOkWithPoints() throws Exception {
        // given
        String requestBody = """
                {
                    "sport": "Long Jump",
                    "result": 7.76
                }
                """;

        // when
        MvcResult result = mockMvc.perform(post("/score/submit")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andReturn();

        // then
        assertEquals(result.getResponse().getStatus(), 200);
        JsonNode json = objectMapper.readTree(result.getResponse().getContentAsString());
        assertEquals(json.get("status").asText(), "ok");
        assertNotNull(json.get("id").asText());
        assertEquals(json.get("sport").asText(), "Long Jump");
        assertEquals(json.get("result").asDouble(), 7.76);
        assertEquals(json.get("points").asInt(), 1000);
    }

    @Test
    public void submit_valid100m_returnsOkWithPoints() throws Exception {
        // given
        String requestBody = """
                {
                    "sport": "100m",
                    "result": 10.395
                }
                """;

        // when
        MvcResult result = mockMvc.perform(post("/score/submit")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andReturn();

        // then
        assertEquals(result.getResponse().getStatus(), 200);
        JsonNode json = objectMapper.readTree(result.getResponse().getContentAsString());
        assertEquals(json.get("status").asText(), "ok");
        assertNotNull(json.get("id").asText());
        assertEquals(json.get("sport").asText(), "100m");
        assertNotNull(json.get("points").asInt());
    }

    @Test
    public void submit_invalidSport_returnsInvalidSportStatus() throws Exception {
        // given
        String requestBody = """
                {
                    "sport": "Unknown Sport",
                    "result": 10.0
                }
                """;

        // when
        MvcResult result = mockMvc.perform(post("/score/submit")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andReturn();

        // then
        assertEquals(result.getResponse().getStatus(), 200);
        JsonNode json = objectMapper.readTree(result.getResponse().getContentAsString());
        assertEquals(json.get("status").asText(), "invalid-sport");
    }

    @Test
    public void submit_negativeResult_returnsInvalidResultStatus() throws Exception {
        // given
        String requestBody = """
                {
                    "sport": "Long Jump",
                    "result": -5.0
                }
                """;

        // when
        MvcResult result = mockMvc.perform(post("/score/submit")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andReturn();

        // then
        assertEquals(result.getResponse().getStatus(), 200);
        JsonNode json = objectMapper.readTree(result.getResponse().getContentAsString());
        assertEquals(json.get("status").asText(), "invalid-result");
    }

}
