package com.pip.chatbot;

import com.pip.chatbot.model.joke.*;
import com.pip.chatbot.repository.forecast.CitiesRepository;
import com.pip.chatbot.repository.forecast.CountriesRepository;
import com.pip.chatbot.repository.forecast.ForecastRepository;
import com.pip.chatbot.repository.joke.JokesRepository;
import org.jooq.DSLContext;
import org.jooq.SQLDialect;
import org.jooq.impl.*;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.springframework.beans.factory.annotation.Qualifier;
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
    public JokesRepository noAuthJokeRepository(DSLContext dsl) {
        return new JokesRepository(dsl);
    }

    @Bean
    @Qualifier("Joke")
    public ModelMapper modelMapperJoke() {
        PropertyMap<JokeApi, Joke> propertyMap = new PropertyMap<JokeApi, Joke>() {
            @Override
            public void configure() {
                map().setJoke(source.getJoke());
                map().setCategory(source.getCategory());
            }
        };
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.addMappings(propertyMap);
        return modelMapper;
    }

    @Bean
    @Qualifier("Mark")
    public ModelMapper modelMapperMark() {
        PropertyMap<MarkApi, Mark> propertyMap = new PropertyMap<MarkApi, Mark>() {
            @Override
            public void configure() {
                map().setMark(source.getMark());
                map().setJokeId(source.getJokeId());
            }
        };
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.addMappings(propertyMap);
        return modelMapper;
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
}
