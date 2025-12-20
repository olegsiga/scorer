package com.scorer.dao;

import com.scorer.entity.ScoreResult;
import com.scorer.entity.Sport;
import lombok.RequiredArgsConstructor;
import org.intellij.lang.annotations.Language;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@Repository
public class ScoreResultDao {

    private final NamedParameterJdbcTemplate jdbcTemplate;

    private static final RowMapper<ScoreResult> ROW_MAPPER = (rs, rowNum) -> new ScoreResult()
            .setId(rs.getString("id"))
            .setSport(Sport.valueOf(rs.getString("sport")))
            .setResult(rs.getDouble("result"))
            .setPoints(rs.getInt("points"))
            .setCreatedAt(rs.getTimestamp("created_at").toInstant());

    @Transactional(rollbackFor = Exception.class)
    public void insert(ScoreResult scoreResult) {
        @Language("SQL")
        var sql = """
                INSERT INTO score_result (id, sport, result, points, created_at)
                VALUES (:id, :sport, :result, :points, :createdAt)
                """;

        var params = new MapSqlParameterSource()
                .addValue("id", scoreResult.getId())
                .addValue("sport", scoreResult.getSport().name())
                .addValue("result", scoreResult.getResult())
                .addValue("points", scoreResult.getPoints())
                .addValue("createdAt", java.sql.Timestamp.from(scoreResult.getCreatedAt()));

        jdbcTemplate.update(sql, params);
    }

    @Transactional(readOnly = true)
    public ScoreResult findById(String id) {
        @Language("SQL")
        var sql = """
                SELECT id, sport, result, points, created_at
                FROM score_result
                WHERE id = :id
                """;

        var results = jdbcTemplate.query(sql, Map.of("id", id), ROW_MAPPER);
        return results.isEmpty() ? null : results.getFirst();
    }

    @Transactional(readOnly = true)
    public List<ScoreResult> findRecent(int limit) {
        @Language("SQL")
        var sql = """
                SELECT id, sport, result, points, created_at
                FROM score_result
                ORDER BY created_at DESC
                LIMIT :limit
                """;

        return jdbcTemplate.query(sql, Map.of("limit", limit), ROW_MAPPER);
    }

}
