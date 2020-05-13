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


import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.jdbc.datasource.TransactionAwareDataSourceProxy;


import javax.sql.DataSource;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Map;


public class WeatherDbUtils {

    private final DSLContext dsl;
    private final CitiesRepository citiesRepository;
    private final CountriesRepository countriesRepository;
    private final ForecastRepository forecastRepository;

    public WeatherDbUtils(Map<String, Object> config)  {

        DataSource dataSource = DataSourceBuilder.create()
                .url(config.get("url").toString())
                .username(config.get("username").toString())
                .password(config.get("password").toString())
                .build();

        DefaultConfiguration jooqConfiguration = new DefaultConfiguration();
        jooqConfiguration.set(new DataSourceConnectionProvider(new TransactionAwareDataSourceProxy(dataSource)));
        jooqConfiguration.set(new DefaultExecuteListenerProvider(new DefaultExecuteListener()));
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
