SET search_path TO jokes;

ALTER TABLE category
    add column is_confirmed boolean DEFAULT true not null;

ALTER TABLE mark
    ALTER COLUMN mark TYPE FLOAT USING (mark::float);
