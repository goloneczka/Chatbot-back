package com.pip.chatbot.integration.weather;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.core.env.Environment;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;

import java.net.http.HttpResponse;
import java.time.LocalDateTime;
public class DarkSkyApi {
    private String key = "5d11f59a5bb65e816b54f9476d2e21c3";
    private String language = "pl";
    private String unitsType = "ca";

    @Autowired
    public DarkSkyConfig darkSkyConfig;

    public void setDarkSkyConfig(DarkSkyConfig darkSkyConfig) {
        this.darkSkyConfig = darkSkyConfig;
    }

    public String getWeatherForecast(float latitude, float longitude) {
        try {
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(new URI("https://api.darksky.net/forecast/" + key + "/" + latitude + ',' + longitude + "?lang=" + language + "&units=" + unitsType + "&exclude=currently,minutely,hourly,alerts,flags"))
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
