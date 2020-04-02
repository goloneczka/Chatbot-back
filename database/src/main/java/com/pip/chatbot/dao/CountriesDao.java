package com.pip.chatbot.dao;

import com.pip.chatbot.jooq.weather.Tables;
import com.pip.chatbot.jooq.weather.tables.records.CountryRecord;
import org.jooq.DSLContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CountriesDao {
    @Autowired
    DSLContext dsl;

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
