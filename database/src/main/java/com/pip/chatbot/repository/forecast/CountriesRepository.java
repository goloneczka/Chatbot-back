package com.pip.chatbot.repository.forecast;

import com.pip.chatbot.jooq.weather.Tables;
import com.pip.chatbot.model.forecast.Country;
import org.jooq.DSLContext;

import java.util.List;

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

    public Country updateCountry(String countryId, Country country) {
        return dsl.update(Tables.COUNTRY)
                .set(Tables.COUNTRY.COUNTRY_, country.getCountry())
                .where(Tables.COUNTRY.COUNTRY_.eq(countryId))
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

    public boolean deleteAll(){
        return 0 < dsl
                .deleteFrom(Tables.COUNTRY)
                .execute();
    }
}
