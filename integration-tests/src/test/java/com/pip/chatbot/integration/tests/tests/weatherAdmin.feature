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
    * json jsonCityUpdate = read('classpath:weather/citiesUpdate.json')
    * json jsonForecast = read('classpath:weather/forecasts.json')
    * json jsonCountry = read('classpath:weather/countries.json')
    * json jsonCountryUpdate = read('classpath:weather/countriesUpdate.json')

    * configure afterFeature =
    """
    function(){
      db.clearWeatherData();
    }
    """
    * header Authorization =  call read('basic-auth.js') {user: #(user), password: #(password)}


  Scenario: insert country
    Given path '/admin/forecasts/countries'
    And request jsonCountryUpdate.countryForUpdate
    When method POST
    Then status 200
    And match $ == jsonCountryUpdate.countryForUpdate

  Scenario: verify inserted country
    * def countryArray = []
    * set countryArray[0] = jsonCountry
    * set countryArray[1] = jsonCountryUpdate.countryForUpdate
    Given path '/admin/forecasts/countries'
    When method GET
    Then status 200
    And match $ == countryArray

  Scenario: update country
    Given path '/admin/forecasts/countries/' + jsonCountryUpdate.countryForUpdate.country
    And request jsonCountryUpdate.countryForDelete
    When method PUT
    Then status 200
    And match $ == jsonCountryUpdate.countryForDelete

  Scenario: get countries
    * def countryArray = []
    * set countryArray[0] = jsonCountry
    * set countryArray[1] = jsonCountryUpdate.countryForDelete
    Given path '/admin/forecasts/countries'
    When method GET
    Then status 200
    And match $ == countryArray

  Scenario: delete country
    Given path '/admin/forecasts/countries/' + jsonCountryUpdate.countryForDelete.country
    When method DELETE
    Then status 200
    And match $ == { "success": true }

  Scenario: insert city
    Given path '/admin/forecasts/cities'
    And request jsonCityUpdate.cityForUpdate
    When method POST
    Then status 200
    And match $ == jsonCityUpdate.cityForUpdate

  Scenario: verify inserted city
    * def cityArray = []
    * set cityArray[0] = jsonCity
    * set cityArray[1] = jsonCityUpdate.cityForUpdate
    Given path '/admin/forecasts/countries/' + jsonCountry.country + '/cities'
    When method GET
    Then status 200
    And match $ == cityArray


  Scenario: update city
    Given path '/admin/forecasts/cities/' + jsonCityUpdate.cityForUpdate.city
    And request jsonCityUpdate.cityForDelete
    When method PUT
    Then status 200
    And match $ == jsonCityUpdate.cityForDelete

  Scenario: get cities
    * def cityArray = []
    * set cityArray[0] = jsonCity
    * set cityArray[1] = jsonCityUpdate.cityForDelete
    Given path '/admin/forecasts/countries/' + jsonCountry.country + '/cities'
    When method GET
    Then status 200
    And match $ == cityArray

  Scenario: delete city
    Given path '/admin/forecasts/cities/' + jsonCityUpdate.cityForDelete.city
    When method DELETE
    Then status 200
    And match $ == { "success": true }

