Feature: Restaurants Api

  Background:
    * url baseUrl
    * def initDatabase =
    """
    function() {
      var DbUtils = Java.type('com.pip.chatbot.integration.tests.WeatherDbUtils');
      var db = new DbUtils(dbConfig);
      db.initWeatherData();
     return db;
    }
    """
    * def db = callonce initDatabase

    * json jsonCity = read('classpath:weather/cities.json')
    * json jsonForecast = read('classpath:weather/forecasts.json')

    * configure afterFeature =
    """
    function(){
      db.clearWeatherData();
    }
    """

  Scenario: Integration test for get forecast
    * def cityArray = []
    * set cityArray[0] = jsonCity
    Given path 'cities'
    When method GET
    Then status 200
    And match $ == cityArray


  Scenario: Integration test for get forecast
    Given path 'forecasts/city/Warszawa'
    And param date = db.getTomorrowDateToString()
    When method GET
    Then status 200
    And match $ contains jsonForecast