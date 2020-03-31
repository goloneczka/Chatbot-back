package com.pip.chatbot.dao;

import com.pip.chatbot.jooq.weather.Tables;
import com.pip.chatbot.jooq.weather.tables.records.CountryRecord;
import org.jooq.DSLContext;
import org.jooq.SQLDialect;
import org.jooq.impl.DSL;

import java.sql.Connection;
import java.sql.DriverManager;

public class CountriesDao {
    private String userName = "postgres";
    private String password = "michal45";
    private String url = "jdbc:postgresql://localhost:5432/postgres";
    public void createCountry(String country){
        try {
            Connection conn = DriverManager.getConnection(url, userName, password);
            DSLContext create = DSL.using(conn, SQLDialect.POSTGRES);
            CountryRecord countryRecord = create.newRecord(Tables.COUNTRY);
            countryRecord.setCountry(country);
            countryRecord.store();

        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
