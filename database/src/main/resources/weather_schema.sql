CREATE SCHEMA weather;
    SET search_path TO weather;
    CREATE TABLE forecast(
        id serial PRIMARY KEY,
		created_on TIMESTAMP NOT NULL,
		date TIMESTAMP NOT NULL,
        temperature_high REAL NOT NULL,
		apparent_temperature_high REAL NOT NULL,
        wind_speed REAL NOT NULL,
		pressure REAL NOT NULL,
		humidity REAL NOT NULL,
		summary char(127) NOT NULL,
		precip_type char(127),
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
