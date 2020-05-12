package com.pip.chatbot.integration.tests;

import org.jooq.DSLContext;
import org.jooq.SQLDialect;
import org.jooq.impl.*;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.datasource.TransactionAwareDataSourceProxy;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;

import static com.pip.chatbot.jooq.food.Food.FOOD;

@Controller
public class foodDbUtils {

    @Bean
    public DataSource getDataSource() {
        DataSourceBuilder dataSourceBuilder = DataSourceBuilder.create();
        dataSourceBuilder.url("jdbc:postgresql://trainings:5432/appdb");
        dataSourceBuilder.username("app");
        dataSourceBuilder.password("Ao4eiT2w");
        return dataSourceBuilder.build();
    }

    @Bean
    public DataSourceConnectionProvider connectionProvider(DataSource dataSource) {
        return new DataSourceConnectionProvider
                (new TransactionAwareDataSourceProxy(dataSource));
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
    public DSLContext dsl(DefaultConfiguration configuration) {
        return new DefaultDSLContext(configuration);
    }

    @Autowired
    private DSLContext dsl;

    public foodDbUtils() {
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

        dsl =  new DefaultDSLContext(jooqConfiguration);

    }

    public void insertCountry(String val) {
        dsl.insertInto(FOOD.COUNTRY)
                .set(FOOD.COUNTRY.COUNTRY_, val)
                .execute();
    }

    public void insertCity(String city, String country) {
        dsl.insertInto(FOOD.CITY)
                .set(FOOD.CITY.CITY_, city)
                .set(FOOD.CITY.COUNTRY, country)
                .execute();
    }

    public void clearFoodSchema(){

        System.out.println("CZYSZCZENIE !");
        dsl.deleteFrom(FOOD.CITY)
                .where(FOOD.CITY.ID.gt(2))
                .execute();

        dsl.deleteFrom(FOOD.COUNTRY)
                .where(true)
                .execute();
    }
}
