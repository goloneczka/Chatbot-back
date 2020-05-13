package com.pip.chatbot.integration.tests;

import com.pip.chatbot.jooq.weather.Tables;
import com.pip.chatbot.model.forecast.City;
import com.pip.chatbot.model.forecast.Country;
import com.pip.chatbot.model.forecast.Forecast;
import com.pip.chatbot.repository.forecast.CitiesRepository;
import com.pip.chatbot.repository.forecast.CountriesRepository;
import com.pip.chatbot.repository.forecast.ForecastRepository;
import org.jooq.DSLContext;
import org.jooq.SQLDialect;
import org.jooq.impl.*;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.jdbc.datasource.TransactionAwareDataSourceProxy;


import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;


public class WeatherDbUtils {

    @Value("${spring.datasource.url}")
    private String url;
    @Value("${spring.datasource.username}")
    private String username;
    @Value("${spring.datasource.password}")
    private String password;

    private final DSLContext dsl;
    private final CitiesRepository citiesRepository;
    private final CountriesRepository countriesRepository;
    private final ForecastRepository forecastRepository;

    public WeatherDbUtils() throws IOException {

        System.out.println("url: " + url);

        DataSourceBuilder dataSourceBuilder = DataSourceBuilder.create();
        dataSourceBuilder.url("jdbc:postgresql://localhost:3307/hycom");
        dataSourceBuilder.username("postgres");
        dataSourceBuilder.password("klopek1432");
        DefaultConfiguration jooqConfiguration = new DefaultConfiguration();
        jooqConfiguration.set(new DataSourceConnectionProvider
                (new TransactionAwareDataSourceProxy(dataSourceBuilder.build())));
        jooqConfiguration
                .set(new DefaultExecuteListenerProvider(new DefaultExecuteListener()));
        jooqConfiguration.setSQLDialect(SQLDialect.POSTGRES);
        dsl = new DefaultDSLContext(jooqConfiguration);

        citiesRepository = new CitiesRepository(dsl);
        countriesRepository = new CountriesRepository(dsl);
        forecastRepository = new ForecastRepository(dsl);
    }


    public void initWeatherData() {
        countriesRepository.createCountry(new Country("Polska"));
        citiesRepository.createCity(new City("Warszawa", 50.22f, 33.12f, "Polska"));
        forecastRepository.createForecasts(new ArrayList<>() {{
            add(new Forecast(null, LocalDateTime.now(), LocalDateTime.now().plusDays(1), 23.4f,
                    22.5f, 9.8f, 1024, 22,
                    "Fake, neutral temperature for tommorow ", "none", "Warszawa", "fakeIcon.url"));
            add(new Forecast(null, LocalDateTime.now(), LocalDateTime.now().plusDays(2), 29.4f,
                    29.5f, 2.8f, 1024, 5,
                    "Fake, hot temperature for tommorow", "none", "Warszawa", "fakeIcon.url"));
        }});
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
}
