CREATE SCHEMA IF NOT EXISTS jokes;
SET search_path TO jokes;
CREATE TABLE IF NOT EXISTS joke
(
    id                    SERIAL PRIMARY KEY,
    category              VARCHAR(127) not null,
    text_joke             TEXT not null
);
