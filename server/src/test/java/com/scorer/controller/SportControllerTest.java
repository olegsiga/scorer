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
import static org.testng.Assert.assertTrue;

public class SportControllerTest extends BaseTest {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Test
    public void list_returnsAllSports() throws Exception {
        // when
        MvcResult result = mockMvc.perform(post("/sport/list")
                        .contentType(MediaType.APPLICATION_JSON))
                .andReturn();

        // then
        assertEquals(result.getResponse().getStatus(), 200);
        JsonNode json = objectMapper.readTree(result.getResponse().getContentAsString());
        assertNotNull(json.get("content"));
        assertTrue(json.get("content").isArray());
        assertEquals(json.get("content").size(), 10);
    }

    @Test
    public void list_containsExpectedSportFields() throws Exception {
        // when
        MvcResult result = mockMvc.perform(post("/sport/list")
                        .contentType(MediaType.APPLICATION_JSON))
                .andReturn();

        // then
        JsonNode json = objectMapper.readTree(result.getResponse().getContentAsString());
        JsonNode firstSport = json.get("content").get(0);
        assertNotNull(firstSport.get("name"));
        assertNotNull(firstSport.get("displayName"));
        assertNotNull(firstSport.get("eventType"));
    }
}
