package com.pip.chatbot.integration.weather;

import com.pip.chatbot.repository.CitiesRepository;
import com.pip.chatbot.repository.CountriesRepository;
import com.pip.chatbot.repository.ForecastRepository;
import org.jooq.ExecuteListener;
import org.jooq.SQLDialect;
import org.jooq.impl.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.TransactionAwareDataSourceProxy;

import javax.sql.DataSource;

@Configuration
public class InitialConfiguration {
    @Autowired
    DataSource dataSource;

    @Bean
    public DataSourceConnectionProvider connectionProvider() {
        return new DataSourceConnectionProvider
                (new TransactionAwareDataSourceProxy(dataSource));
    }

    public DefaultDSLContext dsl() {
        return new DefaultDSLContext(configuration());
    }

    @Bean
    public ForecastRepository forecastRepository() {
        return new ForecastRepository(dsl());
    }

    @Bean
    public CitiesRepository citiesRepository(){
        return new CitiesRepository(dsl());
    }

    @Bean
    public CountriesRepository countriesRepository(){
        return new CountriesRepository(dsl());
    }
    public DefaultConfiguration configuration() {
        DefaultConfiguration jooqConfiguration = new DefaultConfiguration();
        jooqConfiguration.set(connectionProvider());
        jooqConfiguration
                .set(new DefaultExecuteListenerProvider(new DefaultExecuteListener()));
        jooqConfiguration.setSQLDialect(SQLDialect.POSTGRES);
        return jooqConfiguration;
    }
}
