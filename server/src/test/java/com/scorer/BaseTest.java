package com.scorer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.testng.annotations.BeforeMethod;

@ActiveProfiles("test")
@SpringBootTest(classes = {ScorerApplication.class}, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class BaseTest extends AbstractTestNGSpringContextTests {

    protected MockMvc mockMvc;

    @Autowired
    protected WebApplicationContext wac;

    @BeforeMethod
    public void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
    }

}
