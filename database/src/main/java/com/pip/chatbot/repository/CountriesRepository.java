package com.pip.chatbot.repository;

import com.pip.chatbot.jooq.weather.Tables;
import com.pip.chatbot.jooq.weather.tables.records.CountryRecord;
import com.pip.chatbot.model.Country;
import org.jooq.DSLContext;

import java.util.List;

public class CountriesRepository {
    private final DSLContext dsl;

    public CountriesRepository(DSLContext dsl) {
        this.dsl = dsl;
    }

    public Country createCountry(Country country) {
        CountryRecord countryRecord = dsl.newRecord(Tables.COUNTRY);
        countryRecord.setCountry(country.getCountry());
        countryRecord.store();
        return countryRecord.into(Country.class);
    }

    public List<Country> getCountriesList() {
        return dsl.selectFrom(Tables.COUNTRY).fetchInto(Country.class);
    }

    public void deleteCountry(String country) {
        int numberOfRowsAffected = dsl.delete(Tables.COUNTRY).where(Tables.COUNTRY.COUNTRY_.eq(country)).execute();
        if (numberOfRowsAffected < 1) {
            throw new IllegalArgumentException();
        }
    }
}
