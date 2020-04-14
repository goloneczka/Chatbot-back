package com.pip.chatbot.integration.weather;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.pip.chatbot.repository.CitiesRepository;
import com.pip.chatbot.repository.CountriesRepository;
import com.pip.chatbot.repository.ForecastRepository;
import org.jooq.DSLContext;
import org.jooq.SQLDialect;
import org.jooq.impl.*;
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
}
