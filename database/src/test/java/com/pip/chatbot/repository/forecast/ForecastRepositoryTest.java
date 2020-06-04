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

import static com.pip.chatbot.jooq.weather.tables.Forecast.FORECAST;
import static com.pip.chatbot.jooq.weather.tables.City.CITY;
import static com.pip.chatbot.jooq.weather.tables.Country.COUNTRY;

public class ForecastRepositoryTest {

    private DSLContext dslContext;
    private ForecastRepository forecastRepository;

    @BeforeEach
    void init() {
        DslContextFactory dslContextFactory = new DslContextFactory();
        this.dslContext = dslContextFactory.getDslContext();
        CountriesRepository countriesRepository = new CountriesRepository(dslContext);
        countriesRepository.createCountry(new Country("Country"));
        CitiesRepository citiesRepository = new CitiesRepository(dslContext);
        citiesRepository.createCity(new City("City", 1F, 1F, "Country"));
        citiesRepository.createCity(new City("City2",3F,4F,"Country"));

        this.forecastRepository = new ForecastRepository(dslContext);
    }

    @AfterEach
    void clearDatabase() {
        dslContext.truncateTable(FORECAST).cascade().execute();
        dslContext.truncateTable(COUNTRY).cascade().execute();
        dslContext.truncateTable(CITY).cascade().execute();
    }

    void addForecastToDatabase() {
        forecastRepository.createForecast(new Forecast(1, LocalDateTime.of(2000,12,31,5,6), LocalDateTime.of(2000,12,31,5,6), 1, 2, 3, 4, 5, "Summary", "City", "icon"));
    }

    @Test
    void getForecastForCityReturnsListOfForecasts() {
        this.addForecastToDatabase();
        forecastRepository.createForecast(new Forecast(1, forecastRepository.getAllForecasts().get(0).getCreatedOn(),forecastRepository.getAllForecasts().get(0).getDate(), 1, 2, 3, 4, 5, "Summary", "City2", "icon"));

        Assertions.assertThat(forecastRepository.getForecastsForCity("City"))
                .hasSize(1)
                .contains(new Forecast(forecastRepository.getAllForecasts().get(0).getId() , forecastRepository.getAllForecasts().get(0).getCreatedOn(),forecastRepository.getAllForecasts().get(0).getDate(), 1, 2, 3, 4, 5, "Summary", "City", "icon"));
    }

    @Test
    void getForecastForCityWithNoForecastsReturnsEmptyListOfForecasts() {
        this.addForecastToDatabase();

        Assertions.assertThat(forecastRepository.getForecastsForCity("NonExistingCity"))
                .isEmpty();
    }

    @Test
    void getForecastForCityGivenNonExistingCityReturnsEmptyListOfForecasts() {
        Assertions.assertThat(forecastRepository.getForecastsForCity("NonExistingCity"))
                .isEmpty();
    }


    @Test
    void getForecastForCityAndDateReturnsForecast() {
        this.addForecastToDatabase();
        forecastRepository.createForecast(new Forecast(1, LocalDateTime.of(2000,12,31,5,6), LocalDateTime.of(2000,12,31,5,6) , 1, 2, 3, 4, 5, "Summary", "City2", "icon"));
        forecastRepository.createForecast(new Forecast(1, LocalDateTime.of(2000,12,30,5,6), LocalDateTime.of(2000,12,30,5,6) , 1, 2, 3, 4, 5, "Summary", "City", "icon"));

        Assertions.assertThat(forecastRepository.getForecastsForCityAndDate("City", LocalDateTime.of(2000,12,31,5,6)))
                .get()
                .isEqualTo(new Forecast(forecastRepository.getAllForecasts().get(0).getId(), forecastRepository.getAllForecasts().get(0).getCreatedOn(), forecastRepository.getAllForecasts().get(0).getDate(), 1, 2, 3, 4, 5, "Summary", "City", "icon"));
    }

    @Test
    void getForecastForCityAndDateGivenNonExistingCityReturnsEmpty() {
        this.addForecastToDatabase();

        Assertions.assertThat(forecastRepository.getForecastsForCityAndDate("NonExistingCity", LocalDateTime.of(2000,12,31,5,6)))
                .isEmpty();
    }

    @Test
    void getForecastForCityAndDateGivenExistingCityAndNonExistingDateRangeReturnsEmpty() {
        Assertions.assertThat(forecastRepository.getForecastsForCityAndDate("City", LocalDateTime.now().minusDays(8)))
                .isEmpty();
    }
}
