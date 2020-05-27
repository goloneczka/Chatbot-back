Feature: Food Endpoints

  Background:
    * url baseUrl

    * def DbUtils = Java.type('com.pip.chatbot.integration.tests.food.FoodDbUtils')
    * def db = new DbUtils(dbConfig)
    * db.clearDb()
    * json city = db.insertCountryAndCity()
    * json cuisine = db.insertCuisine()
    * json restaurant = db.insertRestaurant()
    * json mark = read('classpath:food/mark.json')
    * configure afterScenario =
    """
    function(){
      db.clearDb()
    }
    """
    Scenario: Get all cities
      Given path '/food/city'
      When method GET
      Then status 200
      And match each response == city

    Scenario: Get cuisines for city
      Given path '/food/city', city.id, '/cuisine'
      When method GET
      Then status 200
      And match each response == cuisine

    Scenario: Get restaurant for cuisine and city
      Given path '/food/city', city.id, '/cuisine', cuisine.cuisine, '/restaurant'
      When method GET
      Then status 200
#      And match response == restaurant

    Scenario: Create mark
      Given path '/food/rate'
      And request mark
      When method POST
      Then status 200
      And match $ == {"restaurantId":#(mark.restaurantId),"mark":#(mark.mark)}