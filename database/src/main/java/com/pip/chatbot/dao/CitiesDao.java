package com.pip.chatbot.dao;

import com.pip.chatbot.jooq.weather.Tables;
import com.pip.chatbot.jooq.weather.tables.records.CityRecord;
import org.jooq.DSLContext;
import org.jooq.Record;
import org.jooq.Result;
import org.jooq.SQLDialect;
import org.jooq.impl.DSL;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.List;

public class CitiesDao {
    private String userName = "postgres";
    private String password = "michal45";
    private String url = "jdbc:postgresql://localhost:5432/postgres";

    public Result<CityRecord> getAllCities(){
        try {
            Connection conn = DriverManager.getConnection(url, userName, password);
            DSLContext create = DSL.using(conn, SQLDialect.POSTGRES);
             return create.selectFrom(Tables.CITY).fetch();
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }
    public void createCity(String city, String country, float latitude, float longitude){
        try {
            Connection conn = DriverManager.getConnection(url, userName, password);
            DSLContext create = DSL.using(conn, SQLDialect.POSTGRES);
            CityRecord cityRecord = create.newRecord(Tables.CITY);
            cityRecord.setCity(city);
            cityRecord.setCountry(country);
            cityRecord.setLatitude(latitude);
            cityRecord.setLongitude(longitude);
            cityRecord.store();

        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
