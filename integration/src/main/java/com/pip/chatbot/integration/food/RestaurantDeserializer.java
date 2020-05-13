package com.pip.chatbot.integration.food;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.pip.chatbot.model.food.Cuisine;
import com.pip.chatbot.model.food.Restaurant;

import java.io.IOException;
import java.util.Arrays;
import java.util.stream.Collectors;

public class RestaurantDeserializer extends StdDeserializer<Restaurant> {

    public RestaurantDeserializer() {
        this(null);
    }

    public RestaurantDeserializer(Class<?> vc) {
        super(vc);
    }

    @Override
    public Restaurant deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JsonProcessingException {
        Restaurant restaurant = new Restaurant();

        JsonNode node = jsonParser.getCodec().readTree(jsonParser);
        restaurant.setId(node.get("id").asInt());
        restaurant.setName(node.get("name").asText());
        restaurant.setAddress(node.get("location").get("address").asText());
        restaurant.setCityId(node.get("location").get("city_id").asInt());
        restaurant.setAverageUsersRating(node.get("user_rating").get("aggregate_rating").asDouble());
        restaurant.setPhoneNumbers(node.get("phone_numbers").asText());
        restaurant.setCuisines(Arrays.stream(node.get("cuisines").asText().split(", ")).map(Cuisine::new).collect(Collectors.toList()));

        return restaurant;
    }
}
