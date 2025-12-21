package com.scorer;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class DatabaseHelper {

    private final JdbcTemplate jdbcTemplate;

    public void clearDatabase() {
        jdbcTemplate.execute("DELETE FROM score_result");
    }

}
