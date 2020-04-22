CREATE SCHEMA IF NOT EXISTS food;
set search_path to food;

CREATE TABLE IF NOT EXISTS cuisine(
    cuisine_id SERIAL PRIMARY KEY,
    cuisine_name VARCHAR(127) NOT NULL
);

CREATE TABLE IF NOT EXISTS country(
    country_id SERIAL PRIMARY KEY,
    country_name VARCHAR(127) NOT NULL
);

CREATE TABLE IF NOT EXISTS city(
    city_id SERIAL PRIMARY KEY,
    city_name VARCHAR(127) NOT NULL,
    country_id SERIAL not null references country(country_id) ON UPDATE CASCADE ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS restaurant(
    restaurant_id SERIAL PRIMARY KEY,
    restaurant_name VARCHAR(127) NOT NULL,
    restaurant_address VARCHAR(127) NOT NULL,
    city_id SERIAL not null references city(city_id) ON UPDATE CASCADE ON DELETE CASCADE,
    average_users_rating REAL,
    phone_numbers VARCHAR(127)
);

CREATE TABLE IF NOT EXISTS restaurant_cuisine(
    restaurant_id SERIAL not null references restaurant(restaurant_id) ON UPDATE CASCADE ON DELETE CASCADE,
    cuisine_id SERIAL not null references cuisine(cuisine_id) ON UPDATE CASCADE ON DELETE CASCADE,
    CONSTRAINT restaurant_cuisine_pkey PRIMARY KEY (restaurant_id, cuisine_id)
);

CREATE TABLE IF NOT EXISTS menu(
    menu_id SERIAL PRIMARY KEY,
    restaurant_id SERIAL not null references restaurant(restaurant_id) ON UPDATE CASCADE ON DELETE CASCADE,
    start_date TIMESTAMP NOT NULL,
    end_date TIMESTAMP
);

CREATE TABLE IF NOT EXISTS dish(
    dish_id SERIAL PRIMARY KEY,
    dish_name VARCHAR(127) NOT NULL
);

CREATE TABLE IF NOT EXISTS menu_dish(
    menu_id SERIAL not null references menu(menu_id) ON UPDATE CASCADE ON DELETE CASCADE,
    dish_id SERIAL not null references dish(dish_id) ON UPDATE CASCADE ON DELETE CASCADE,
    price REAL NOT NULL,
    CONSTRAINT restaurant_cuisine_primaryKey PRIMARY KEY (menu_id, dish_id)
)


