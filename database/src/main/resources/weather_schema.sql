CREATE SCHEMA weather;
    SET search_path TO weather;
    CREATE TABLE forecast(
        id serial PRIMARY KEY,
		created_on TIMESTAMP NOT NULL,
		date TIMESTAMP NOT NULL,
        temperature REAL NOT NULL,
		perceived_temperature REAL NOT NULL,
        wind_power REAL NOT NULL,
		atmospheric_pressure REAL NOT NULL,
		air_humidity REAL NOT NULL,
		summary char(127) NOT NULL,
		precipType char(127),
		city VARCHAR(127) NOT NULL REFERENCES city(city) ON UPDATE CASCADE ON DELETE CASCADE
    );
    CREATE TABLE city(
        city VARCHAR(127) NOT NULL PRIMARY KEY,
        country VARCHAR(127) NOT NULL REFERENCES country(country) ON UPDATE CASCADE ON DELETE CASCADE
        latitude REAL NOT NULL,
        longitude REAL NOT NULL
    );
    CREATE TABLE country(
        country VARCHAR(127) PRIMARY KEY
    )
