package com.pip.chatbot.integration.tests;

import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
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
import org.springframework.jdbc.datasource.TransactionAwareDataSourceProxy;


import javax.sql.DataSource;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class WeatherDbUtils {

    private final DSLContext dsl;
    private final CitiesRepository citiesRepository;
    private final CountriesRepository countriesRepository;
    private final ForecastRepository forecastRepository;

    private Map<String, Object> initializeData = new HashMap<>() {{
        put("countryWrapper", new Country("Polska"));
        put("cityWrapper", new City("Warszawa", 50.22f, 33.12f, "Polska"));
        put("forecastWrapper", new Forecast(null, LocalDateTime.now(), LocalDateTime.now().plusDays(1), 23.4f, 22.5f, 9.8f, 1024, 22, "Fake, neutral temperature for tommorow", "none", "Warszawa", "fakeIcon.url"));
    }};

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


    public void initWeatherData() {
        countriesRepository.createCountry((Country) initializeData.get("countryWrapper"));
        citiesRepository.createCity((City) initializeData.get("cityWrapper"));
        forecastRepository.createForecast((Forecast) initializeData.get("forecastWrapper"));
    }

    public String getWeatherJsonData() {
        GsonForecast gsonForecast = new GsonForecast((Forecast) initializeData.get("forecastWrapper"));
        initializeData.put("forecastWrapper", gsonForecast);
        return new GsonBuilder().create().toJson(initializeData, HashMap.class);
    }


    public String getTomorrowDateToString() {
        return LocalDateTime.now().plusDays(1).format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
    }

    public void clearWeatherData() {
        countriesRepository.deleteCountry("Polska");
        citiesRepository.deleteCity("Warszawa");
        dsl.deleteFrom(Tables.FORECAST)
                .where(Tables.FORECAST.CITY.eq("Warszawa"));
    }


    @Data
    protected class GsonForecast {
        private Integer id;
        private float temperature;
        private float perceivedTemperature;
        private float windSpeed;
        private float pressure;
        private float humidity;
        private String city;
        private String icon;

        public GsonForecast(Forecast forecastWrapper) {
            this.id = forecastWrapper.getId();
            this.temperature = forecastWrapper.getTemperature();
            this.perceivedTemperature = forecastWrapper.getPerceivedTemperature();
            this.windSpeed = forecastWrapper.getWindSpeed();
            this.pressure = forecastWrapper.getPressure();
            this.humidity = forecastWrapper.getHumidity();
            this.city = forecastWrapper.getCity();
            this.icon = forecastWrapper.getIcon();
        }
    }
}
