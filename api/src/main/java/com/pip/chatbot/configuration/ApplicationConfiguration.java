package com.pip.chatbot.configuration;

import org.jooq.DSLContext;
import org.jooq.SQLDialect;
import org.jooq.impl.DSL;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

import java.sql.DriverManager;
import java.sql.SQLException;

@Configuration
public class ApplicationConfiguration {
    private final YAMLConfig config;

    private final String url;
    private final String username;
    private final String password;

    @Autowired
    public ApplicationConfiguration(YAMLConfig config) {
        this.config = config;

        var dbConfig = config.getDb();
        this.url = dbConfig.get("url");
        this.username = dbConfig.get("username");
        this.password = dbConfig.get("password");
    }

    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }

    @Bean
    public DSLContext dslContext() throws SQLException {
        var con = DriverManager.getConnection(url, username, password);
        return DSL.using(con, SQLDialect.POSTGRES);
    }
}