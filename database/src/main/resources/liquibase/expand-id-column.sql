-- liquibase formatted sql logicalFilePath:expand-id-column.sql

-- changeset expand-id:scorer
ALTER TABLE score_result MODIFY COLUMN id VARCHAR(36);
