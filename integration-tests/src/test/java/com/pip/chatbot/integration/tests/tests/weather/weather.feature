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

    * json cities = read('classpath:weather/cities.json')
    * json forecasts = read('classpath:weather/forecasts.json')

    * configure afterFeature =
    """
    function(){
      db.clearWeatherData();
    }
    """

  Scenario: get cities
    Given path 'cities'
    When method GET
    Then status 200
    And match $ == cities


  Scenario: get forecast
    * set forecasts[0].id = '#number'
    Given path 'forecasts/city/' + cities[0].city
    And param date = db.getTomorrowDateToString()
    When method GET
    Then status 200
    And match $ == forecasts[0]