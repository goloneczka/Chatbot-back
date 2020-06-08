SET search_path TO fortune;

ALTER TABLE stock
    ALTER column date TYPE DATE USING (date::TIMESTAMP);
