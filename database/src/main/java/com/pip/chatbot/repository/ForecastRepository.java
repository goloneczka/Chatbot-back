package com.pip.chatbot.repository;

import com.pip.chatbot.jooq.weather.Tables;
import com.pip.chatbot.jooq.weather.tables.records.ForecastRecord;
import com.pip.chatbot.model.Forecast;
import org.jooq.Condition;
import org.jooq.DSLContext;
import org.jooq.impl.DSL;

import java.time.LocalDateTime;
import java.util.List;

public class ForecastRepository {
    private final DSLContext dsl;

    public ForecastRepository(DSLContext dsl) {
        this.dsl = dsl;
    }

    public void createForecast(Forecast forecast) {
        Condition dayCondition = (DSL.dayOfYear(Tables.FORECAST.DATE)).eq(forecast.getDate().getDayOfYear());
        Condition yearCondition = DSL.year(Tables.FORECAST.DATE).eq(forecast.getDate().getYear());
        Condition cityCondition = Tables.FORECAST.CITY.equal(forecast.getCity());

        ForecastRecord forecastRecord = dsl.selectFrom(Tables.FORECAST).where(dayCondition).and(yearCondition).and(cityCondition).fetchOptional().orElse(dsl.newRecord(Tables.FORECAST));
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
    }

    public void createForecasts(List<Forecast> forecasts) {
        dsl.transaction(outer -> {
            for (Forecast forecast : forecasts) {
                createForecast(forecast);
            }
        });
    }
}
