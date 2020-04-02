package com.pip.chatbot.dao;

import com.pip.chatbot.jooq.weather.Tables;
import com.pip.chatbot.jooq.weather.tables.records.ForecastRecord;
import com.pip.chatbot.model.Forecast;
import org.jooq.Condition;
import org.jooq.DSLContext;
import org.jooq.impl.DSL;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class ForecastDao {
    @Autowired
    DSLContext dsl;

    public void createForecast(Forecast forecast) {
        try {
            Condition dayCondition = (DSL.dayOfYear(Tables.FORECAST.DATE)).eq(forecast.getDate().getDayOfYear());
            Condition yearCondition = DSL.year(Tables.FORECAST.DATE).eq(forecast.getDate().getYear());
            Condition cityCondition = Tables.FORECAST.CITY.equal(forecast.getCity());

            ForecastRecord forecastRecord;
            if (dsl.fetchExists(dsl.select(DSL.count()).from(Tables.FORECAST).where(dayCondition).and(cityCondition).and(yearCondition))) {
                forecastRecord = dsl.selectFrom(Tables.FORECAST).where(dayCondition).and(yearCondition).and(cityCondition).fetchAny();
            } else {
                forecastRecord = dsl.newRecord(Tables.FORECAST);
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
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
