package com.pip.chatbot.repository;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.pip.chatbot.jooq.weather.Tables;
import com.pip.chatbot.jooq.weather.tables.records.CityRecord;
import com.pip.chatbot.model.City;
import org.jooq.DSLContext;
import org.jooq.JSONFormat;

import java.util.List;

public class CitiesRepository {
    DSLContext dsl;
    public CitiesRepository(DSLContext dsl){
        this.dsl = dsl;
    }

    public List<City> getAllCities() {
        try {
            String citiesJson = dsl.selectFrom(Tables.CITY).fetch().formatJSON(new JSONFormat()
                    .header(false)
                    .recordFormat(JSONFormat.RecordFormat.OBJECT));
            return new ObjectMapper().readValue(citiesJson, new TypeReference<>(){});
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public void createCity(City city) {
        try {
            CityRecord cityRecord = dsl.newRecord(Tables.CITY);
            cityRecord.setCity(city.getCity());
            cityRecord.setCountry(city.getCountry());
            cityRecord.setLatitude(city.getLatitude());
            cityRecord.setLongitude(city.getLongitude());
            cityRecord.store();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
