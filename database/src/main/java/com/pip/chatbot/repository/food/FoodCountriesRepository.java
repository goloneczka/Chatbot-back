package com.pip.chatbot.repository.food;

import com.pip.chatbot.jooq.food.Tables;
import com.pip.chatbot.model.food.Country;
import org.jooq.DSLContext;

import java.util.List;

public class FoodCountriesRepository {
    private final DSLContext dsl;

    public FoodCountriesRepository(DSLContext dsl) {
        this.dsl = dsl;
    }

    public List<Country> getAllCountries() {
        return dsl.selectFrom(Tables.COUNTRY).fetchInto(Country.class);
    }

    public Country createCountry(Country country) {
        return dsl.insertInto(Tables.COUNTRY)
                .columns(Tables.COUNTRY.COUNTRY_)
                .values(country.getCountry())
                .onDuplicateKeyUpdate()
                .set(Tables.COUNTRY.COUNTRY_, country.getCountry())
                .returning()
                .fetchOne()
                .into(Country.class);
    }

    public boolean deleteCountry(String country) {
        int numberOfRowsAffected = dsl.delete(Tables.COUNTRY)
                .where(Tables.COUNTRY.COUNTRY_.eq(country))
                .execute();
        return numberOfRowsAffected >= 1;
    }
}
