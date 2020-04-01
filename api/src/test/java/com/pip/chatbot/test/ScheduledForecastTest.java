package com.pip.chatbot.test;

import com.pip.chatbot.integration.weather.DarkSkyApi;
import com.pip.chatbot.integration.weather.DarkSkyConfig;
import com.pip.chatbot.weather.ScheduledForecasts;
import org.junit.jupiter.api.Test;

public class ScheduledForecastTest {
    @Test
    void saveForecastToDbTest(){
        ScheduledForecasts scheduledForecasts = new ScheduledForecasts();
        scheduledForecasts.saveForecastToDb();
        /*DarkSkyConfig darkSkyConfig = new DarkSkyConfig();
        DarkSkyApi darkSkyApi = new DarkSkyApi();
        System.out.println(darkSkyConfig.getLanguage());
        System.out.println(darkSkyApi.darkSkyConfig.getLanguage());*/
    }
}
