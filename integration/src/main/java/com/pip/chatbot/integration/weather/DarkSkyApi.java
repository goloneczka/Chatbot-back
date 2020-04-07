package com.pip.chatbot.integration.weather;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.pip.chatbot.model.City;
import com.pip.chatbot.model.Forecast;
import org.springframework.stereotype.Component;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.LocalDateTime;
import java.util.List;

@Component
public class DarkSkyApi {
    private final DarkSkyConfig darkSkyConfig;
    private final ObjectMapper objectMapper;

    public DarkSkyApi(DarkSkyConfig darkSkyConfig, ObjectMapper objectMapper) {
        this.darkSkyConfig = darkSkyConfig;
        this.objectMapper = objectMapper;
    }

    public List<Forecast> getWeatherForecast(City city) throws Exception {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(new URI("https://api.darksky.net/forecast/" + darkSkyConfig.getKey() + "/" + city.getLatitude() + ',' + city.getLongitude() + "?lang=" + darkSkyConfig.getLanguage() + "&units=" + darkSkyConfig.getUnitsType() + "&exclude=currently,minutely,hourly,alerts,flags"))
                .GET()
                .build();
        HttpResponse<String> response = HttpClient.newHttpClient()
                .send(request, HttpResponse.BodyHandlers.ofString());

        String forecastsJson = objectMapper.readTree(response.body()).get("daily").get("data").toString();
        List<Forecast> forecasts = objectMapper.readValue(forecastsJson, new TypeReference<List<Forecast>>() {
        });

        for (int i = 0; i < forecasts.size(); i++) {
            forecasts.get(i).setCreatedOn(LocalDateTime.now());
            forecasts.get(i).setDate(LocalDateTime.now().plusDays(i));
            forecasts.get(i).setCity(city.getCity());
        }
        return forecasts;
    }
}
