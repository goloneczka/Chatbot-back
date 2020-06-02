package com.pip.chatbot.repository.forecast;

import com.pip.chatbot.model.forecast.City;
import com.pip.chatbot.model.forecast.Country;
import com.pip.chatbot.model.forecast.Forecast;
import com.pip.chatbot.repository.DslContextFactory;
import org.assertj.core.api.Assertions;
import org.jooq.DSLContext;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


import java.time.LocalDateTime;

import static com.pip.chatbot.jooq.weather.tables.City.CITY;
import static com.pip.chatbot.jooq.weather.tables.Country.COUNTRY;

public class CitiesRepositoryTest {

    private DSLContext dslContext;
    private CitiesRepository citiesRepository;

    @BeforeEach
    void init(){
        DslContextFactory dslContextFactory = new DslContextFactory();
        this.dslContext = dslContextFactory.getDslContext();
        CountriesRepository countriesRepository = new CountriesRepository(dslContext);
        countriesRepository.createCountry(new Country("Country"));

        this.citiesRepository = new CitiesRepository(dslContext);
    }

    @AfterEach
    void clearDatabase(){
        dslContext.truncateTable(CITY).cascade().execute();
        dslContext.truncateTable(COUNTRY).cascade().execute();
    }

    void addCityToDatabase(){

        this.citiesRepository.createCity(new City("City",1.11F,2.22F,"Country"));
    }

    @Test
    void getCityReturnsCity() {
        this.addCityToDatabase();

        Assertions.assertThat(citiesRepository.getCity("City"))
                .get()
                .isEqualTo(new City("City",1.11F,2.22F,"Country"));
    }

    @Test
    void getCityGivenNonExistingCityReturnsEmpty() {
        Assertions.assertThat(citiesRepository.getCity("City"))
                .isEmpty();
    }

    @Test
    void doesCityExistReturnsTrue() {
        this.addCityToDatabase();

        Assertions.assertThat(citiesRepository.doesCityExist("City"))
                .isTrue();
    }

    @Test
    void doesCityExistGivenNonExistingCityReturnsFalse() {
        Assertions.assertThat(citiesRepository.doesCityExist("City"))
                .isFalse();
    }

    @Test
    void getAllCitiesReturnsListCities() {
        this.addCityToDatabase();

        Assertions.assertThat(citiesRepository.getAllCities())
                .isNotEmpty()
                .contains(new City("City",1.11F,2.22F,"Country"));
    }

    @Test
    void getAllCitiesReturnsEmptyList() {
        Assertions.assertThat(citiesRepository.getAllCities())
                .isEmpty();
    }

    @Test
    void createCityReturnsCity() {
        City city = new City("City",1.11F,2.22F,"Country");

        Assertions.assertThat(citiesRepository.createCity(city))
                .isEqualTo(city);
    }

    @Test
    void updateCityReturnsCity() {
        this.addCityToDatabase();
        City updatedCity = new City("CityUpdated",1.11F,2.22F,"Country");

        Assertions.assertThat(citiesRepository.updateCity("City", updatedCity))
                .get()
                .isEqualTo(updatedCity);
    }

    @Test
    void updateCityGivenNonExistingCityReturnsEmpty() {
        City updatedCity = new City("CityUpdated",1.11F,2.22F,"Country");

        Assertions.assertThat(citiesRepository.updateCity("City", updatedCity))
                .isEmpty();
    }

    @Test
    void deleteCityReturnsTrue() {
        this.addCityToDatabase();

        Assertions.assertThat(citiesRepository.deleteCity("City"))
                .isTrue();
    }

    @Test
    void deleteCityGivenNonExistingCityReturnsFalse() {
        Assertions.assertThat(citiesRepository.deleteCity("City"))
                .isFalse();
    }

    @Test
    void getCitiesForCountryReturnsListCities() {
        this.addCityToDatabase();

        Assertions.assertThat(citiesRepository.getCitiesForCountry("Country"))
                .isNotEmpty()
                .contains(new City("City",1.11F,2.22F,"Country"));
    }

    @Test
    void getCitiesForCountryReturnsEmptyList() {
        Assertions.assertThat(citiesRepository.getCitiesForCountry("Country"))
                .isEmpty();
    }

    @Test
    void getCitiesForCountryGivenNonExistingCountryReturnsEmpty() {
        this.addCityToDatabase();

        Assertions.assertThat(citiesRepository.getCitiesForCountry("Country1"))
                .isEmpty();
    }


    @Test
    void getCitiesWithForecastReturnsListCities() {
        this.addCityToDatabase();
        ForecastRepository forecastRepository = new ForecastRepository(dslContext);
        forecastRepository.createForecast(new Forecast(1, LocalDateTime.now(),LocalDateTime.now(),1,2,3,4,5,"Summary","City","icon"));

        Assertions.assertThat(citiesRepository.getCitiesWithForecast())
                .isNotEmpty()
                .contains(new City("City",1.11F,2.22F,"Country"));
    }

    @Test
    void getCitiesWithForecastWithNonGivenForecastReturnsEmptyList() {
        this.addCityToDatabase();

        Assertions.assertThat(citiesRepository.getCitiesWithForecast())
                .isEmpty();
    }


    @Test
    void getCitiesWithForecastWithEmptyCityTableReturnsEmpty() {
        Assertions.assertThat(citiesRepository.getCitiesWithForecast())
                .isEmpty();
    }

    @Test
    void getCitiesForCountryWithForecastReturnsListCities() {
        this.addCityToDatabase();
        ForecastRepository forecastRepository = new ForecastRepository(dslContext);
        forecastRepository.createForecast(new Forecast(1, LocalDateTime.now(),LocalDateTime.now(),1,2,3,4,5,"Summary","City","icon"));

        Assertions.assertThat(citiesRepository.getCitiesForCountryWithForecast("Country"))
                .isNotEmpty()
                .contains(new City("City",1.11F,2.22F,"Country"));
    }

    @Test
    void getCitiesForCountryWithForecastWithNoForecastForCountryTableReturnsEmpty() {
        this.addCityToDatabase();

        Assertions.assertThat(citiesRepository.getCitiesForCountryWithForecast("Country"))
                .isEmpty();
    }

    @Test
    void getCitiesForCountryWithForecastGivenNonExistingCountryReturnsEmpty() {
        this.addCityToDatabase();

        Assertions.assertThat(citiesRepository.getCitiesForCountryWithForecast("Country1"))
                .isEmpty();
    }



}
