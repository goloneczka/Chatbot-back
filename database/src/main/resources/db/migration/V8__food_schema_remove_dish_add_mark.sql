set search_path to food;

DROP TABLE IF EXISTS menu CASCADE;
DROP TABLE IF EXISTS menu_dish CASCADE;
DROP TABLE IF EXISTS dish CASCADE;

CREATE TABLE IF NOT EXISTS mark_restaurant
(
    id                    SERIAL PRIMARY KEY,
    restaurant_id         INTEGER not null references restaurant(id) ON UPDATE CASCADE ON DELETE CASCADE,
    mark                  REAL not null
);
