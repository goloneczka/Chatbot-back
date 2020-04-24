set search_path to weather;
ALTER TABLE forecast
    ADD COLUMN icon VARCHAR(127) NOT NULL;