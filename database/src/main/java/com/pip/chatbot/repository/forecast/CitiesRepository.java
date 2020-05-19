package com.pip.chatbot.repository.forecast;

import com.pip.chatbot.jooq.weather.Tables;
import com.pip.chatbot.model.forecast.City;
import com.pip.chatbot.model.forecast.Country;
import org.jooq.DSLContext;

import java.util.List;
import java.util.Optional;

public class CitiesRepository {
    private final DSLContext dsl;

    public CitiesRepository(DSLContext dsl) {
        this.dsl = dsl;
    }

    public Optional<City> getCity(String cityName) {
        return Optional.ofNullable(dsl.selectFrom(Tables.CITY)
                .where(Tables.CITY.CITY_.eq(cityName))
                .fetchAnyInto(City.class));
    }

    public boolean doesCityExist(String city) {
        return dsl.fetchExists(dsl.selectFrom(Tables.CITY).where(Tables.CITY.CITY_.eq(city)));
    }

    public List<City> getAllCities() {
        return dsl.selectFrom(Tables.CITY).fetchInto(City.class);
    }

    public City createCity(City city) {
        return dsl.insertInto(Tables.CITY)
                .set(Tables.CITY.CITY_, city.getCity())
                .set(Tables.CITY.COUNTRY, city.getCountry())
                .set(Tables.CITY.LATITUDE, city.getLatitude())
                .set(Tables.CITY.LONGITUDE, city.getLongitude())
                .returning()
                .fetchOne()
                .into(City.class);
    }

    public City updateCity(String cityId, City city) {
        return dsl.update(Tables.CITY)
                .set(Tables.CITY.CITY_, city.getCity())
                .set(Tables.CITY.COUNTRY, city.getCountry())
                .set(Tables.CITY.LATITUDE, city.getLatitude())
                .set(Tables.CITY.LONGITUDE, city.getLongitude())
                .where(Tables.CITY.CITY_.eq(cityId))
                .returning()
                .fetchOne()
                .into(City.class);
    }

    public boolean deleteCity(String city) {
        int numberOfRowsAffected = dsl.delete(Tables.CITY)
                .where(Tables.CITY.CITY_.eq(city))
                .execute();
        return numberOfRowsAffected >= 1;
    }

    public List<City> getCitiesForCountry(String country) {
        return dsl.selectFrom(Tables.CITY)
                .where(Tables.CITY.COUNTRY.eq(country))
                .fetchInto(City.class);
    }

    public List<City> getCitiesWithForecast() {
        return dsl.selectDistinct()
                .from(Tables.CITY)
                .where(Tables.CITY.CITY_.in(
                        dsl.select(Tables.FORECAST.CITY)
                                .from(Tables.FORECAST)))
                .fetchInto(City.class);
    }

    public List<City> getCitiesForCountryWithForecast(String country){
        return dsl.selectDistinct()
                .from(Tables.CITY)
                .where(Tables.CITY.CITY_.in(
                        dsl.select(Tables.FORECAST.CITY)
                                .from(Tables.FORECAST)))
                .and(Tables.CITY.COUNTRY.eq(country))
                .fetchInto(City.class);
    }
}
