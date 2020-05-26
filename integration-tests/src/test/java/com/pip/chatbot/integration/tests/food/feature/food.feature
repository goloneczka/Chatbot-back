Feature: Food Endpoints

  Background:
    * url baseUrl

    * def DbUtils = Java.type('com.pip.chatbot.integration.tests.food.FoodDbUtils')
    * def db = new DbUtils(dbConfig)
    * db.clearDb()
    * json city = db.insertCountryAndCity()
    * json cuisine = db.insertCuisine()
    * json restaurant = db.insertRestaurant()
    * json mark = db.insertMark()
    * configure afterScenario =
    """
    function(){
      db.clearDb()
    }
    """
    Scenario: get all cities
      Given path '/food/city'
      When method GET
      Then status 200
      And match each response == city

    Scenario:
      Given path '/food/city', city.id, '/cuisine'
      When method Get
      Then status 200
      And match each response == cuisine

    Scenario:
      Given path '/food/city', city.id, '/cuisine', cuisine.cuisine, '/restaurant'
      When method Get
      Then status 200
#      And match response == restaurant