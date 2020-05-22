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
    * json cityModify = read('classpath:weather/citiesModification.json')
    * json countries = read('classpath:weather/countries.json')
    * json countryModify = read('classpath:weather/countriesModification.json')

    * configure afterFeature =
    """
    function(){
      db.clearWeatherData();
    }
    """

    * def correctAuthorization = callonce read('basic-auth.js') {user: #(user), password: #(password)}
    * def wrongAuthorization = call read('basic-auth.js') {user: #(user), password: #(incorrectPassword)}
    * header Authorization = correctAuthorization

  Scenario: get countries
    Given path '/admin/forecasts/countries'
    When method GET
    Then status 200
    And match $ == countries

  Scenario: insert and verify country
    Given path '/admin/forecasts/countries'
    And request countryModify.countryInsert
    When method POST
    Then status 200
    And match $ == countryModify.countryInsert

    * header Authorization = correctAuthorization
    * set countries[1] = countryModify.countryInsert
    Given path '/admin/forecasts/countries'
    When method GET
    Then status 200
    And match $ == countries


  Scenario: update and verify country
    Given path '/admin/forecasts/countries/' + countryModify.countryInsert.country
    And request countryModify.countryUpdate
    When method PUT
    Then status 200
    And match $ == countryModify.countryUpdate

    * header Authorization = correctAuthorization
    * set countries[1] = countryModify.countryUpdate
    Given path '/admin/forecasts/countries'
    When method GET
    Then status 200
    And match $ == countries


  Scenario: delete and verify country
    Given path '/admin/forecasts/countries/' + countryModify.countryUpdate.country
    When method DELETE
    Then status 200
    And match $ == { "success": true }

    * header Authorization = correctAuthorization
    Given path '/admin/forecasts/countries'
    When method GET
    Then status 200
    And match $ == countries


  Scenario: get and verify cities
    Given path '/admin/forecasts/countries/' + countries[0].country + '/cities'
    When method GET
    Then status 200
    And match $ == cities

  Scenario: insert and verify city
    Given path '/admin/forecasts/cities'
    And request cityModify.cityInsert
    When method POST
    Then status 200
    And match $ == cityModify.cityInsert

    * header Authorization = correctAuthorization
    * set cities[1] = cityModify.cityInsert
    Given path '/admin/forecasts/countries/' + countries[0].country + '/cities'
    When method GET
    Then status 200
    And match $ == cities


  Scenario: update and verify city
    Given path '/admin/forecasts/cities/' + cityModify.cityInsert.city
    And request cityModify.cityUpdate
    When method PUT
    Then status 200
    And match $ == cityModify.cityUpdate

    * header Authorization = correctAuthorization
    * set cities[1] = cityModify.cityUpdate
    Given path '/admin/forecasts/countries/' + countries[0].country + '/cities'
    When method GET
    Then status 200
    And match $ == cities


  Scenario: delete and verify city
    Given path '/admin/forecasts/cities/' + cityModify.cityUpdate.city
    When method DELETE
    Then status 200
    And match $ == { "success": true }

    * header Authorization = correctAuthorization
    Given path '/admin/forecasts/countries/' + countries[0].country + '/cities'
    When method GET
    Then status 200
    And match $ == cities



  Scenario: get countries with wrong authorization
    * header Authorization = wrongAuthorization
    Given path '/admin/forecasts/countries'
    When method GET
    Then status 401

  Scenario: insert countries with wrong authorization
    * header Authorization = wrongAuthorization
    Given path '/admin/forecasts/countries'
    And request countryModify.countryInsert
    When method POST
    Then status 401



  Scenario: update countries with wrong authorization
    * header Authorization = wrongAuthorization
    Given path '/admin/forecasts/countries/' + countryModify.countryInsert.country
    And request countryModify.countryUpdate
    When method PUT
    Then status 401



  Scenario: delete countries with wrong authorization
    * header Authorization = wrongAuthorization
    Given path '/admin/forecasts/countries/' + countryModify.countryUpdate.country
    When method DELETE
    Then status 401


  Scenario: get cities with wrong authorization
    * header Authorization = wrongAuthorization
    Given path '/admin/forecasts/countries/' + countries[0].country + '/cities'
    When method GET
    Then status 401

  Scenario: insert cities with wrong authorization
    * header Authorization = wrongAuthorization
    Given path '/admin/forecasts/cities'
    And request cityModify.cityInsert
    When method POST
    Then status 401


  Scenario: update cities with wrong authorization
    * header Authorization = wrongAuthorization
    Given path '/admin/forecasts/cities/' + cityModify.cityInsert.city
    And request cityModify.cityUpdate
    When method PUT
    Then status 401


  Scenario: delete cities with wrong authorization
    * header Authorization = wrongAuthorization
    Given path '/admin/forecasts/cities/' + cityModify.cityUpdate.city
    When method DELETE
    Then status 401




