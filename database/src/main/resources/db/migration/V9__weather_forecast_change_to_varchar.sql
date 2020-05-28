SET search_path TO weather;

ALTER TABLE forecast
    DROP column precip_type;

ALTER TABLE forecast
    ALTER column summary TYPE VARCHAR(127) USING (summary::VARCHAR(127));