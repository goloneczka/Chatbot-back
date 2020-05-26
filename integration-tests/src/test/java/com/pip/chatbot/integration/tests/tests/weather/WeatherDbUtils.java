package com.pip.chatbot.integration.tests.tests.weather;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import com.pip.chatbot.jooq.weather.Tables;
import com.pip.chatbot.model.forecast.City;
import com.pip.chatbot.model.forecast.Country;
import com.pip.chatbot.model.forecast.Forecast;
import com.pip.chatbot.repository.forecast.CitiesRepository;
import com.pip.chatbot.repository.forecast.CountriesRepository;
import com.pip.chatbot.repository.forecast.ForecastRepository;
import org.jooq.DSLContext;
import org.jooq.impl.*;
import java.io.IOException;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;


public class WeatherDbUtils {

    private final DSLContext dsl;
    private final CitiesRepository citiesRepository;
    private final CountriesRepository countriesRepository;
    private final ForecastRepository forecastRepository;

    public WeatherDbUtils(Map<String, String> config) throws SQLException {
        dsl = DSL.using(DriverManager.getConnection(config.get("url"), config.get("username"), config.get("password")));

        citiesRepository = new CitiesRepository(dsl);
        countriesRepository = new CountriesRepository(dsl);
        forecastRepository = new ForecastRepository(dsl);
    }


    public void initWeatherData() throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        List<City> cities = objectMapper.readValue(WeatherDbUtils.class.getResourceAsStream("/weather/cities.json"), new TypeReference<>() {});
        List<Country> countries = objectMapper.readValue(WeatherDbUtils.class.getResourceAsStream("/weather/countries.json"), new TypeReference<>() {});
        List<Forecast> forecasts = objectMapper.readValue(WeatherDbUtils.class.getResourceAsStream("/weather/forecasts.json"), new TypeReference<>() {});

        Forecast temp = forecasts.get(0);
        temp.setCreatedOn(LocalDateTime.now());
        temp.setDate(LocalDateTime.now().plusDays(1));

        countries.forEach(countriesRepository::createCountry);
        cities.forEach(citiesRepository::createCity);
        forecastRepository.createForecast(temp);
    }

    public String getTomorrowDateToString() {
        return LocalDateTime.now().plusDays(1).format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
    }

    private boolean deleteAll() {
        return 0 < dsl
                .deleteFrom(Tables.COUNTRY)
                .execute();
    }

    public void clearWeatherData() {
        deleteAll();
    }


}
