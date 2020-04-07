package com.pip.chatbot.repository;

import com.pip.chatbot.jooq.weather.Tables;
import com.pip.chatbot.jooq.weather.tables.records.CityRecord;
import com.pip.chatbot.model.City;
import org.jooq.DSLContext;

import java.util.List;

public class CitiesRepository {
    private final DSLContext dsl;

    public CitiesRepository(DSLContext dsl) {
        this.dsl = dsl;
    }

    public List<City> getAllCities() {
        return dsl.selectFrom(Tables.CITY).fetchInto(City.class);
    }

    public void createCity(City city) {
        CityRecord cityRecord = dsl.newRecord(Tables.CITY);

        cityRecord.setCity(city.getCity());
        cityRecord.setCountry(city.getCountry());
        cityRecord.setLatitude(city.getLatitude());
        cityRecord.setLongitude(city.getLongitude());
        cityRecord.store();
    }
}
