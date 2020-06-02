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
    void getForecastForCityReturnsListForecast() {
        this.addForecastToDatabase();

        Assertions.assertThat(forecastRepository.getForecastsForCity("City"))
                .contains(new Forecast(forecastRepository.getAllForecasts().get(0).getId() , forecastRepository.getAllForecasts().get(0).getCreatedOn(),forecastRepository.getAllForecasts().get(0).getDate(), 1, 2, 3, 4, 5, "Summary", "City", "icon"));
    }

    @Test
    void getForecastForCityWithNoForecastsReturnsEmpty() {
        Assertions.assertThat(forecastRepository.getForecastsForCity("City"))
                .isEmpty();
    }

    @Test
    void getForecastForCityGivenNonExistingCityReturnsEmpty() {
        Assertions.assertThat(forecastRepository.getForecastsForCity("City1"))
                .isEmpty();
    }


    @Test
    void getForecastForCityAndDateReturnsForecast() {
        this.addForecastToDatabase();

        Assertions.assertThat(forecastRepository.getForecastsForCityAndDate("City", LocalDateTime.of(2000,12,31,5,6)))
                .get()
                .isEqualTo(new Forecast(forecastRepository.getAllForecasts().get(0).getId(), forecastRepository.getAllForecasts().get(0).getCreatedOn(), forecastRepository.getAllForecasts().get(0).getDate(), 1, 2, 3, 4, 5, "Summary", "City", "icon"));
    }

    @Test
    void getForecastForCityAndDateGivenNonExistingCityReturnsEmpty() {
        Assertions.assertThat(forecastRepository.getForecastsForCityAndDate("City1", LocalDateTime.of(2000,12,31,5,6)))
                .isEmpty();
    }

    @Test
    void getForecastForCityAndDateGivenNonWrongDataReturnsEmpty() {
        Assertions.assertThat(forecastRepository.getForecastsForCityAndDate("City", LocalDateTime.now().minusDays(8)))
                .isEmpty();
    }
}
