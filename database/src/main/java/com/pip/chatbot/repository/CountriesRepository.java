package com.pip.chatbot.repository;

import com.pip.chatbot.jooq.weather.Tables;
import com.pip.chatbot.model.Country;
import org.jooq.DSLContext;

import java.util.List;
import java.util.Optional;

public class CountriesRepository {
    private final DSLContext dsl;

    public CountriesRepository(DSLContext dsl) {
        this.dsl = dsl;
    }

    public Country createCountry(Country country) {
        return dsl.insertInto(Tables.COUNTRY)
                .set(Tables.COUNTRY.COUNTRY_, country.getCountry())
                .returning()
                .fetchOne()
                .into(Country.class);
    }

    public boolean doesCountryExist(String country) {
        return dsl.fetchExists(dsl.selectFrom(Tables.COUNTRY).where(Tables.COUNTRY.COUNTRY_.eq(country)));
    }

    public List<Country> getCountriesList() {
        return dsl.selectFrom(Tables.COUNTRY).fetchInto(Country.class);
    }

    public boolean deleteCountry(String country) {
        int numberOfRowsAffected = dsl.delete(Tables.COUNTRY)
                .where(Tables.COUNTRY.COUNTRY_.eq(country))
                .execute();
        return numberOfRowsAffected >= 1;
    }
}
