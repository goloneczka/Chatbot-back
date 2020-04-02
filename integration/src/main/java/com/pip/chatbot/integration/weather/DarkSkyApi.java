package com.pip.chatbot.integration.weather;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

@Component
public class DarkSkyApi {
    @Autowired
    public DarkSkyConfig darkSkyConfig;

    public String getWeatherForecast(float latitude, float longitude) {
        try {
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(new URI("https://api.darksky.net/forecast/" + darkSkyConfig.getKey() + "/" + latitude + ',' + longitude + "?lang=" + darkSkyConfig.getLanguage() + "&units=" + darkSkyConfig.getUnitsType() + "&exclude=currently,minutely,hourly,alerts,flags"))
                    .GET()
                    .build();
            HttpResponse<String> response = HttpClient.newHttpClient()
                    .send(request, HttpResponse.BodyHandlers.ofString());
            return new ObjectMapper().readTree(response.body()).get("daily").get("data").toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
