package com.pip.chatbot.repository;

import com.pip.chatbot.jooq.weather.Tables;
import com.pip.chatbot.jooq.weather.tables.records.CountryRecord;
import org.jooq.DSLContext;

public class CountriesRepository {
    private final DSLContext dsl;

    public CountriesRepository(DSLContext dsl) {
        this.dsl = dsl;
    }

    public void createCountry(String country) {
        CountryRecord countryRecord = dsl.newRecord(Tables.COUNTRY);
        countryRecord.setCountry(country);
        countryRecord.store();
    }
}
