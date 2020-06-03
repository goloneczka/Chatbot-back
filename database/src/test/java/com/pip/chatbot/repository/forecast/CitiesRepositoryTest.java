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
    private ForecastRepository forecastRepository;
    private City city;

    @BeforeEach
    void init() {
        DslContextFactory dslContextFactory = new DslContextFactory();
        this.dslContext = dslContextFactory.getDslContext();
        CountriesRepository countriesRepository = new CountriesRepository(dslContext);
        countriesRepository.createCountry(new Country("Country"));
        this.city = new City("City", 1.11F, 2.22F, "Country");
        this.forecastRepository = new ForecastRepository(dslContext);

        this.citiesRepository = new CitiesRepository(dslContext);
    }

    @AfterEach
    void clearDatabase() {
        dslContext.truncateTable(CITY).cascade().execute();
        dslContext.truncateTable(COUNTRY).cascade().execute();
    }

    void addCityToDatabase() {
        this.citiesRepository.createCity(this.city);
    }

    @Test
    void getCityReturnsCity() {
        this.addCityToDatabase();

        Assertions.assertThat(citiesRepository.getCity("City"))
                .get()
                .isEqualTo(this.city);
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
                .isNotEmpty().contains(this.city);

        Assertions.assertThat(citiesRepository.getCity(this.city.getCity()))
                .get().isEqualTo(this.city);
    }

    @Test
    void getAllCitiesReturnsEmptyList() {
        Assertions.assertThat(citiesRepository.getAllCities())
                .isEmpty();
    }

    @Test
    void createCityReturnsCity() {
        Assertions.assertThat(citiesRepository.createCity(this.city))
                .isEqualTo(city);

        Assertions.assertThat(citiesRepository.getCity(city.getCity()))
                .get()
                .isEqualTo(city);
    }

    @Test
    void updateCityReturnsCity() {
        this.addCityToDatabase();
        City updatedCity = new City("CityUpdated", 1.11F, 2.22F, "Country");

        Assertions.assertThat(citiesRepository.updateCity("City", updatedCity))
                .get().isEqualTo(updatedCity);

        Assertions.assertThat(citiesRepository.getCity(updatedCity.getCity()))
                .get().isEqualTo(updatedCity);
    }

    @Test
    void updateCityGivenNonExistingCityReturnsEmpty() {
        City updatedCity = new City("CityUpdated", 1.11F, 2.22F, "Country");

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
    void getCitiesForCountryReturnsListOfCities() {
        this.addCityToDatabase();

        Assertions.assertThat(citiesRepository.getCitiesForCountry("Country"))
                .isNotEmpty()
                .contains(this.city);
    }

    @Test
    void getCitiesForCountryReturnsEmptyListOfCities() {
        Assertions.assertThat(citiesRepository.getCitiesForCountry("Country"))
                .isEmpty();
    }

    @Test
    void getCitiesForCountryGivenNonExistingCountryReturnsEmptyListOfCities() {
        this.addCityToDatabase();

        Assertions.assertThat(citiesRepository.getCitiesForCountry("Country1"))
                .isEmpty();
    }


    @Test
    void getCitiesWithForecastReturnsListOfCities() {
        this.addCityToDatabase();
        citiesRepository.createCity(new City("City1",3.33F,4.44F,"Country"));
        forecastRepository.createForecast(new Forecast(1, LocalDateTime.now(), LocalDateTime.now(), 1, 2, 3, 4, 5, "Summary", "City", "icon"));

        Assertions.assertThat(citiesRepository.getCitiesWithForecast())
                .hasSize(1)
                .contains(this.city);
    }

    @Test
    void getCitiesWithForecastWithNonGivenForecastReturnsEmptyListOfCities() {
        this.addCityToDatabase();

        Assertions.assertThat(citiesRepository.getCitiesWithForecast())
                .isEmpty();
    }


    @Test
    void getCitiesWithForecastWithEmptyCityTableReturnsEmptyListOfCities() {
        Assertions.assertThat(citiesRepository.getCitiesWithForecast())
                .isEmpty();
    }

    @Test
    void getCitiesForCountryWithForecastReturnsListOfCities() {
        this.addCityToDatabase();
        citiesRepository.createCity(new City("City1",3.33F,4.44F,"Country"));
        forecastRepository.createForecast(new Forecast(1, LocalDateTime.now(), LocalDateTime.now(), 1, 2, 3, 4, 5, "Summary", "City", "icon"));

        Assertions.assertThat(citiesRepository.getCitiesForCountryWithForecast("Country"))
                .hasSize(1)
                .contains(new City("City", 1.11F, 2.22F, "Country"));
    }

    @Test
    void getCitiesForCountryWithForecastWithNoForecastForCountryTableReturnsEmptyListOfCities() {
        this.addCityToDatabase();

        Assertions.assertThat(citiesRepository.getCitiesForCountryWithForecast("Country"))
                .isEmpty();
    }

    @Test
    void getCitiesForCountryWithForecastGivenNonExistingCountryReturnsEmptyListOfCities() {
        this.addCityToDatabase();

        Assertions.assertThat(citiesRepository.getCitiesForCountryWithForecast("Country1"))
                .isEmpty();
    }


}
