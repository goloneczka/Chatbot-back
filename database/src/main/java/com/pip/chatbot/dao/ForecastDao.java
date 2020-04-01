package com.pip.chatbot.dao;

import com.pip.chatbot.jooq.weather.Sequences;
import com.pip.chatbot.jooq.weather.Tables;
import com.pip.chatbot.jooq.weather.tables.records.ForecastRecord;
import com.pip.chatbot.model.Forecast;
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

    public void createForecast(Forecast forecast){
        try{
            Connection conn = DriverManager.getConnection(url, userName, password);
            DSLContext create = DSL.using(conn, SQLDialect.POSTGRES);
            Condition dayCondition = DSL.dayOfYear(Tables.FORECAST.DATE).eq(DSL.dayOfYear(forecast.getDate()));
            Condition yearCondition = DSL.year(Tables.FORECAST.DATE).eq(DSL.year(forecast.getDate()));
            Condition cityCondition = Tables.FORECAST.CITY.eq(forecast.getCity());
            ForecastRecord forecastRecord;
            if(create.fetchExists(create.selectFrom(Tables.FORECAST).where(dayCondition).and(yearCondition).and(cityCondition))) {
                forecastRecord = create.selectFrom(Tables.FORECAST).where(dayCondition).and(yearCondition).and(cityCondition).fetchAny();
            }
            else {
                forecastRecord = create.newRecord(Tables.FORECAST);
                Field<Integer> id_seq = Sequences.FORECAST_ID_SEQ.nextval();
                forecastRecord.setId(create.select(id_seq).fetchOne(id_seq));
            }

            forecastRecord.setCreatedOn(LocalDateTime.now());
            forecastRecord.setDate(forecast.getDate());
            forecastRecord.setPerceivedTemperature(forecast.getApparentTemperatureHigh());
            forecastRecord.setTemperature(forecast.temperatureHigh);
            forecastRecord.setWindPower(forecast.getWindSpeed());
            forecastRecord.setAtmosphericPressure(forecast.getPressure());
            forecastRecord.setAirHumidity(forecast.humidity);
            forecastRecord.setSummary(forecast.getSummary());
            forecastRecord.setPreciptype(forecast.getPrecipType());
            forecastRecord.setCity(forecast.getCity());
            forecastRecord.store();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
