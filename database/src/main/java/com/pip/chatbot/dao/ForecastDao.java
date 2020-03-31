package com.pip.chatbot.dao;

import com.pip.chatbot.jooq.weather.Sequences;
import com.pip.chatbot.jooq.weather.Tables;
import com.pip.chatbot.jooq.weather.tables.records.ForecastRecord;
import org.jooq.Condition;
import org.jooq.DSLContext;
import org.jooq.Field;
import org.jooq.SQLDialect;
import org.jooq.impl.DSL;

import java.sql.Connection;
import java.sql.DriverManager;
import java.time.LocalDateTime;

public class ForecastDao {
    private String userName = "postgres";
    private String password = "michal45";
    private String url = "jdbc:postgresql://localhost:5432/postgres";

    public void createForecast(LocalDateTime date, float temperature, float perceived_temperature, float wind_power, float atmospheric_pressure, float air_humidity, String summary, String precipType, String city){
        try{
            Connection conn = DriverManager.getConnection(url, userName, password);
            DSLContext create = DSL.using(conn, SQLDialect.POSTGRES);
            Condition dayCondition = DSL.dayOfYear(Tables.FORECAST.DATE).eq(DSL.dayOfYear(date));
            Condition yearCondition = DSL.year(Tables.FORECAST.DATE).eq(DSL.year(date));
            Condition cityCondition = Tables.FORECAST.CITY.eq(city);
            ForecastRecord forecastRecord;
            System.out.println(create.fetchExists(create.selectFrom(Tables.FORECAST).where(dayCondition).and(yearCondition).and(cityCondition)));
            if(create.fetchExists(create.selectFrom(Tables.FORECAST).where(dayCondition).and(yearCondition).and(cityCondition))) {
                forecastRecord = create.selectFrom(Tables.FORECAST).where(dayCondition).and(yearCondition).and(cityCondition).fetchAny();
            }
            else {
                forecastRecord = create.newRecord(Tables.FORECAST);
                Field<Integer> id_seq = Sequences.FORECAST_ID_SEQ.nextval();
                forecastRecord.setId(create.select(id_seq).fetchOne(id_seq));
            }

            forecastRecord.setCreatedOn(LocalDateTime.now());
            forecastRecord.setDate(date);
            forecastRecord.setPerceivedTemperature(perceived_temperature);
            forecastRecord.setTemperature(temperature);
            forecastRecord.setWindPower(wind_power);
            forecastRecord.setAtmosphericPressure(atmospheric_pressure);
            forecastRecord.setAirHumidity(air_humidity);
            forecastRecord.setSummary(summary);
            forecastRecord.setPreciptype(precipType);
            forecastRecord.setCity(city);
            forecastRecord.store();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
