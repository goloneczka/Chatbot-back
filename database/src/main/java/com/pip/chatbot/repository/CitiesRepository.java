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

    public City getCity(String cityName) throws IllegalArgumentException {
        City city = dsl.selectFrom(Tables.CITY).where(Tables.CITY.CITY_.eq(cityName)).fetchAnyInto(City.class);
        if (city == null) {
            throw new IllegalArgumentException();
        }
        return city;
    }

    public List<City> getAllCities() {
        return dsl.selectFrom(Tables.CITY).fetchInto(City.class);
    }

    public City createCity(City city) {
        CityRecord cityRecord = dsl.newRecord(Tables.CITY);

        cityRecord.setCity(city.getCity());
        cityRecord.setCountry(city.getCountry());
        cityRecord.setLatitude(city.getLatitude());
        cityRecord.setLongitude(city.getLongitude());
        cityRecord.store();
        System.out.println(cityRecord.into(City.class));
        return cityRecord.into(City.class);
    }

    public void deleteCity(String city) throws IllegalArgumentException {
        int numberOfRowsAffected = dsl.delete(Tables.CITY).where(Tables.CITY.CITY_.eq(city)).execute();
        if (numberOfRowsAffected < 1) {
            throw new IllegalArgumentException();
        }
        ;
    }

    public List<City> getCitiesForCountry(String country) throws IllegalArgumentException {
        //Checking if given country is correct
        if (!dsl.fetchExists(dsl.selectFrom(Tables.COUNTRY).where(Tables.COUNTRY.COUNTRY_.eq(country)))) {
            throw new IllegalArgumentException();
        }
        return dsl.selectFrom(Tables.CITY).where(Tables.CITY.COUNTRY.eq(country)).fetchInto(City.class);
    }
}
