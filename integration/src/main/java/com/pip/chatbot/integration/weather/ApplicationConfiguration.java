package com.pip.chatbot.integration.weather;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.pip.chatbot.integration.model.ForecastApi;
import com.pip.chatbot.model.forecast.Forecast;
import com.pip.chatbot.repository.forecast.CitiesRepository;
import com.pip.chatbot.repository.forecast.CountriesRepository;
import com.pip.chatbot.repository.forecast.ForecastRepository;
import com.pip.chatbot.repository.joke.NoAuthJokeRepository;
import org.jooq.DSLContext;
import org.jooq.SQLDialect;
import org.jooq.impl.*;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.TransactionAwareDataSourceProxy;

import javax.sql.DataSource;

@Configuration
public class ApplicationConfiguration {
    @Bean
    public DataSourceConnectionProvider connectionProvider(DataSource dataSource) {
        return new DataSourceConnectionProvider
                (new TransactionAwareDataSourceProxy(dataSource));
    }

    @Bean
    public DSLContext dsl(DefaultConfiguration configuration) {
        return new DefaultDSLContext(configuration);
    }

    @Bean
    public ForecastRepository forecastRepository(DSLContext dsl) {
        return new ForecastRepository(dsl);
    }

    @Bean
    public CitiesRepository citiesRepository(DSLContext dsl) {
        return new CitiesRepository(dsl);
    }

    @Bean
    public CountriesRepository countriesRepository(DSLContext dsl) {
        return new CountriesRepository(dsl);
    }

    @Bean
    public NoAuthJokeRepository noAuthJokeRepository(DSLContext dsl) {
        return new NoAuthJokeRepository(dsl);
    }

    @Bean
    public DefaultConfiguration configuration(DataSourceConnectionProvider connectionProvider) {
        DefaultConfiguration jooqConfiguration = new DefaultConfiguration();
        jooqConfiguration.set(connectionProvider);

        jooqConfiguration
                .set(new DefaultExecuteListenerProvider(new DefaultExecuteListener()));
        jooqConfiguration.setSQLDialect(SQLDialect.POSTGRES);

        return jooqConfiguration;
    }

    @Bean
    public ObjectMapper objectMapper() {
        return new ObjectMapper()
                .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
                .configure(DeserializationFeature.FAIL_ON_NULL_FOR_PRIMITIVES, false);
    }

    @Bean
    public PropertyMap<ForecastApi, Forecast> propertyMap() {
        return new PropertyMap<ForecastApi, Forecast>() {
            @Override
            public void configure() {
                map().setTemperature(source.getTemperatureHigh());
                map().setPerceivedTemperature(source.getApparentTemperatureHigh());
            }
        };
    }

    @Bean
    public ModelMapper modelMapper(PropertyMap<ForecastApi, Forecast> propertyMap) {
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.addMappings(propertyMap);
        return modelMapper;
    }


}
