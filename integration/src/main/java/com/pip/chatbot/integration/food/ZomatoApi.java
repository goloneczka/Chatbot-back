package com.pip.chatbot.integration.food;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.pip.chatbot.model.food.City;
import com.pip.chatbot.model.food.Menu;
import com.pip.chatbot.model.food.Restaurant;
import org.springframework.stereotype.Component;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;

@Component
public class ZomatoApi {
    private final ZomatoConfig zomatoConfig;
    private final ObjectMapper objectMapper;


    public ZomatoApi(ZomatoConfig zomatoConfig, ObjectMapper objectMapper){
        this.zomatoConfig = zomatoConfig;
        this.objectMapper = objectMapper;
    }

    public List<Restaurant> getRestaurantsData(City city) throws Exception {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(new URI("https://developers.zomato.com/api/v2.1/search?entity_id=" + city.getId() + "&entity_type=city"))
                .header("user-key", zomatoConfig.getKey())
                .GET()
                .build();
        HttpResponse<String> response = HttpClient.newHttpClient()
                .send(request, HttpResponse.BodyHandlers.ofString());

        String restaurantDataJSON = objectMapper.readTree(response.body()).get("restaurants").findValues("restaurant").toString();

        return objectMapper.readValue(restaurantDataJSON, new TypeReference<List<Restaurant>>() {});
    }

    public Menu getMenusData(Restaurant restaurant) throws Exception {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(new URI("https://developers.zomato.com/api/v2.1/dailymenu?res_id=" + restaurant.getId()))
                .header("user-key", zomatoConfig.getKey())
                .GET()
                .build();
        HttpResponse<String> response = HttpClient.newHttpClient()
                .send(request, HttpResponse.BodyHandlers.ofString());
        System.out.println(response.toString());
        return null;
    }
}
