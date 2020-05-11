package com.pip.chatbot.integration.food;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.pip.chatbot.model.food.City;
import com.pip.chatbot.model.food.Restaurant;
import org.springframework.stereotype.Component;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;

@Component
public class ZomatoApi {
    private final ZomatoConfig zomatoConfig;
    private final ObjectMapper objectMapper;
    private final int restaurantsPerPage;
    private final int maxPageNumber;


    public ZomatoApi(ZomatoConfig zomatoConfig, ObjectMapper objectMapper) {
        this.zomatoConfig = zomatoConfig;
        this.objectMapper = objectMapper;
        this.restaurantsPerPage = 20;
        this.maxPageNumber = 5;
    }

    public List<Restaurant> getRestaurantsData(City city) throws Exception {
        List<Restaurant> allRestaurants = new ArrayList<>();
        int pageNumber = 0;
        int pagesAmount = maxPageNumber;

        do {
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(new URI("https://developers.zomato.com/api/v2.1/search?entity_id=" + city.getId() + "&entity_type=city&sort=rating&start=" + pageNumber * this.restaurantsPerPage))
                    .header("user-key", zomatoConfig.getKey())
                    .GET()
                    .build();

            HttpResponse<String> response = HttpClient.newHttpClient()
                    .send(request, HttpResponse.BodyHandlers.ofString());

            String restaurantDataJSON = objectMapper.readTree(response.body()).get("restaurants").findValues("restaurant").toString();

            allRestaurants.addAll(objectMapper.readValue(restaurantDataJSON, new TypeReference<List<Restaurant>>() {
            }));

            pagesAmount = (int) Math.min(Math.ceil(objectMapper.readTree(response.body()).get("results_found").floatValue()), this.maxPageNumber);

        } while (pageNumber++ < pagesAmount);


        return allRestaurants;
    }

}
