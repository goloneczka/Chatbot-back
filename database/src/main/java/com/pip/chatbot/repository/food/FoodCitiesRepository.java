package com.pip.chatbot.repository.food;

import com.pip.chatbot.jooq.food.Tables;
import com.pip.chatbot.model.food.City;
import org.jooq.DSLContext;

import java.util.List;
import java.util.Optional;

public class FoodCitiesRepository {
    private final DSLContext dsl;

    public FoodCitiesRepository(DSLContext dsl) {
        this.dsl = dsl;
    }

    public Optional<City> getCity(int id) {
        return Optional.ofNullable(dsl.selectFrom(Tables.CITY)
                .where(Tables.CITY.ID.eq(id))
                .fetchAnyInto(City.class));
    }

    public List<City> getAllCities() {
        return dsl.selectFrom(Tables.CITY).fetchInto(City.class);
    }

    public City createCity(City city) {
        return dsl.insertInto(Tables.CITY)
                .columns(Tables.CITY.ID, Tables.CITY.CITY_, Tables.CITY.COUNTRY)
                .values(city.getId(), city.getCity(), city.getCountry())
                .onDuplicateKeyUpdate()
                .set(Tables.CITY.CITY_, city.getCity())
                .set(Tables.CITY.COUNTRY, city.getCountry())
                .returning()
                .fetchOne()
                .into(City.class);
    }

    public boolean deleteCity(int id) {
        int numberOfRowsAffected = dsl.delete(Tables.CITY)
                .where(Tables.CITY.ID.eq(id))
                .execute();
        return numberOfRowsAffected >= 1;
    }
}
