SET search_path TO jokes;

ALTER TABLE joke
    add column is_confirmed boolean DEFAULT true not null;

CREATE TABLE IF NOT EXISTS mark
(
    id                    SERIAL PRIMARY KEY,
    joke_id               INTEGER not null references joke(id) ON UPDATE CASCADE ON DELETE CASCADE,
    mark                  DECIMAL not null
);