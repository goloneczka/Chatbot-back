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

    * def initData =
    """
    function() {
      return db.getWeatherJsonData();
    }
    """
    * json jsonWeatherData = callonce initData

    * configure afterFeature =
    """
    function(){
      db.clearWeatherData();
    }
    """
    * header Authorization =  call read('basic-auth.js') {user: #(user), password: #(password)}


  Scenario: insert country
    Given path '/admin/forecasts/countries'
    And request { "country": "CountryToUpdate" }
    When method POST
    Then status 200
    And match $ == { "country": "CountryToUpdate" }

  Scenario: update country
    Given path '/admin/forecasts/countries/CountryToUpdate'
    And request { "country": "countryForDelete" }
    When method PUT
    Then status 200
    And match $ == { "country": "countryForDelete" }

  Scenario: get countries
    * def countryArray = []
    * set countryArray[0] = jsonWeatherData.countryWrapper
    * set countryArray[1] = {"country": "countryForDelete"}
    Given path '/admin/forecasts/countries'
    When method GET
    Then status 200
    And match $ == countryArray

  Scenario: delete country
    Given path '/admin/forecasts/countries/countryForDelete'
    When method DELETE
    Then status 200
    And match $ == { "success": true }

  Scenario: insert city
    Given path '/admin/forecasts/cities'
    And request { "city": "locationToUpdate", "country": "Polska", "latitude": 50.42, "longitude": 34.12 }
    When method POST
    Then status 200
    And match $ == { "city": "locationToUpdate", "country": "Polska", "latitude": 50.42, "longitude": 34.12 }

  Scenario: update city
    Given path '/admin/forecasts/cities/locationToUpdate'
    And request { "city": "locationForDelete", "country": "Polska", "latitude": 50.42, "longitude": 34.12 }
    When method PUT
    Then status 200
    And match $ == { "city": "locationForDelete", "country": "Polska", "latitude": 50.42, "longitude": 34.12 }

  Scenario: get cities
    * def cityArray = []
    * set cityArray[0] = jsonWeatherData.cityWrapper
    * set cityArray[1] = {"city": "locationForDelete", "country": "Polska", "latitude": 50.42, "longitude": 34.12}
    Given path '/admin/forecasts/countries/Polska/cities'
    When method GET
    Then status 200
    And match $ == cityArray

  Scenario: delete city
    Given path '/admin/forecasts/cities/locationForDelete'
    When method DELETE
    Then status 200
    And match $ == { "success": true }

