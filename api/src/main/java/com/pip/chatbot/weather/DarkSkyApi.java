package com.pip.chatbot.weather;

import com.pip.chatbot.dao.CitiesDao;
import com.pip.chatbot.dao.ForecastDao;
import com.pip.chatbot.jooq.weather.tables.records.CityRecord;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import java.time.LocalDateTime;

@EnableScheduling
public class DarkSkyApi {
    public JSONArray getWeatherForecast(float latitude, float longitude) {
        CloseableHttpClient httpClient = HttpClients.createDefault();
        String weatherApiKey = "5d11f59a5bb65e816b54f9476d2e21c3";
        HttpGet request = new HttpGet("https://api.darksky.net/forecast/" + weatherApiKey + "/" + latitude + ',' + longitude + "?lang=pl&units=ca");
        JSONObject result = new JSONObject();
        try (CloseableHttpResponse response = httpClient.execute(request)) {
            HttpEntity entity = response.getEntity();

            if (entity != null) {
                result = new JSONObject(EntityUtils.toString(entity));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return result.getJSONObject("daily").getJSONArray("data");
    }

    @Scheduled(fixedDelay = 86400000)
    public void saveForecastToDb(){
        CitiesDao citiesDao = new CitiesDao();
        ForecastDao forecastDao = new ForecastDao();
        for(CityRecord cityRecord: citiesDao.getAllCities()){
            JSONArray forecast_json = getWeatherForecast(cityRecord.getLatitude(),cityRecord.getLongitude());
            for(int i = 0; i<forecast_json.length(); i++){
                JSONObject forecastForDay = forecast_json.getJSONObject(i);
                String precipType;
                if(forecastForDay.getDouble("precipProbability") <= 0.5){
                    precipType = "brak";
                }
                else{
                    precipType = forecastForDay.getString("precipType");
                }
                forecastDao.createForecast(LocalDateTime.now().plusDays(i), (float)forecastForDay.getDouble("temperatureHigh"),
                        (float)forecastForDay.getDouble("apparentTemperatureHigh"), (float)forecastForDay.getDouble("windSpeed"),
                        (float)forecastForDay.getDouble("pressure"), (float)forecastForDay.getDouble("humidity"),
                        forecastForDay.getString("summary"), precipType, cityRecord.getCity());
            }


        }
    }
}
