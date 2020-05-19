package com.pip.chatbot.integration.tests;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pip.chatbot.jooq.weather.Tables;
import com.pip.chatbot.model.forecast.City;
import com.pip.chatbot.model.forecast.Country;
import com.pip.chatbot.model.forecast.Forecast;
import com.pip.chatbot.repository.forecast.CitiesRepository;
import com.pip.chatbot.repository.forecast.CountriesRepository;
import com.pip.chatbot.repository.forecast.ForecastRepository;
import lombok.Data;
import org.jooq.DSLContext;
import org.jooq.SQLDialect;
import org.jooq.impl.*;


import org.springframework.boot.jdbc.DataSourceBuilder;


import javax.sql.DataSource;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Map;


public class WeatherDbUtils {

    private final DSLContext dsl;
    private final CitiesRepository citiesRepository;
    private final CountriesRepository countriesRepository;
    private final ForecastRepository forecastRepository;

    public WeatherDbUtils(Map<String, String> config) {

        DataSource dataSource = DataSourceBuilder.create()
                .url(config.get("url"))
                .username(config.get("username"))
                .password(config.get("password"))
                .build();

        DefaultConfiguration jooqConfiguration = new DefaultConfiguration();
        jooqConfiguration.set(dataSource);
        jooqConfiguration.setSQLDialect(SQLDialect.POSTGRES);
        dsl = new DefaultDSLContext(jooqConfiguration);

        citiesRepository = new CitiesRepository(dsl);
        countriesRepository = new CountriesRepository(dsl);
        forecastRepository = new ForecastRepository(dsl);
    }


    public void initWeatherData() throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        City city = objectMapper.readValue(City.class.getResourceAsStream("/weather/cities.json"), City.class);
        Country country = objectMapper.readValue(Country.class.getResourceAsStream("/weather/countries.json"), Country.class);
        Forecast forecast = objectMapper.readValue(Forecast.class.getResourceAsStream("/weather/forecasts.json"), Forecast.class);
        forecast.setCreatedOn(LocalDateTime.now());
        forecast.setDate(LocalDateTime.now().plusDays(1));
        forecast.setSummary("Fake forecast for capital city.");

        countriesRepository.createCountry(country);
        citiesRepository.createCity(city);
        forecastRepository.createForecast(forecast);
    }


    public String getTomorrowDateToString() {
        return LocalDateTime.now().plusDays(1).format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
    }

    public void clearWeatherData() {
        countriesRepository.deleteCountry("Polska");
    }

}
