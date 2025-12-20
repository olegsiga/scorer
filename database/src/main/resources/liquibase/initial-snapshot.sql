-- liquibase formatted sql logicalFilePath:initial-snapshot.sql

-- changeset init:scorer

CREATE TABLE score_result (
    id VARCHAR(32) PRIMARY KEY,
    sport VARCHAR(50) NOT NULL,
    result DOUBLE NOT NULL,
    points INT NOT NULL,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);

CREATE INDEX idx_score_result_created_at ON score_result(created_at DESC);
