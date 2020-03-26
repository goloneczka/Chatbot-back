CREATE SCHEMA weather;
    SET search_path TO weather;
    CREATE TABLE forecast(
        id serial PRIMARY KEY,
		created_on TIMESTAMP NOT NULL,
		date TIMESTAMP NOT NULL,
        temperature REAL NOT NULL,
		perceived_temperature REAL NOT NULL,
        wind_power REAL NOT NULL,
		wind_direction CHAR(2) NOT NULL,
		atmospheric_pressure REAL NOT NULL,
		air_humidity REAL NOT NULL,
		city VARCHAR(25) NOT NULL
    )

