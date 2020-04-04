package com.pip.chatbot.repository;

import com.pip.chatbot.jooq.weather.Tables;
import com.pip.chatbot.jooq.weather.tables.records.CountryRecord;
import org.jooq.DSLContext;

public class CountriesRepository {
    DSLContext dsl;
    public CountriesRepository(DSLContext dsl){
        this.dsl = dsl;
    }
    public void createCountry(String country) {
        try {
            CountryRecord countryRecord = dsl.newRecord(Tables.COUNTRY);
            countryRecord.setCountry(country);
            countryRecord.store();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
