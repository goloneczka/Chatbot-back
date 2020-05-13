Feature: Restaurants Api

  Background:
    * url baseUrl
    * def initData =
  """
  function() {
    var DbUtils = Java.type('com.pip.chatbot.integration.tests.WeatherDbUtils');
    var db = new DbUtils(dbConfig);
    db.initWeatherData();
    return db;
  }
  """
    * def db = callonce initData
    * configure afterFeature =
    """
      function(){
      db.clearWeatherData();
      }
    """


  Scenario: Integration test for insert country
    * header Authorization =  call read('basic-auth.js') {user: #(user), password: #(password)}
    Given path '/admin/forecasts/countries'
    And request { "country": "CountryToUpdate" }
    When method POST
    Then status 200

  Scenario: Integration test for update country
    * header Authorization =  call read('basic-auth.js') {user: #(user), password: #(password)}
    Given path '/admin/forecasts/countries/CountryToUpdate'
    And request { "country": "countryForDelete" }
    When method PUT
    Then status 200

  Scenario: Integration test for get countries
    * header Authorization =  call read('basic-auth.js') {user: #(user), password: #(password)}
    Given path '/admin/forecasts/countries'
    When method GET
    Then status 200
    And match $ == [{"country": "Polska"},{"country": "countryForDelete"}]

  Scenario: Integration test for delete country
    * header Authorization =  call read('basic-auth.js') {user: #(user), password: #(password)}
    Given path '/admin/forecasts/countries/countryForDelete'
    When method DELETE
    Then status 200

  Scenario: Integration test for insert city
    * header Authorization =  call read('basic-auth.js') {user: #(user), password: #(password)}
    Given path '/admin/forecasts/cities'
    And request { "city": "locationToUpdate", "country": "Polska", "latitude": 50.42, "longitude": 34.12 }
    When method POST
    Then status 200

  Scenario: Integration test for update city
    * header Authorization =  call read('basic-auth.js') {user: #(user), password: #(password)}
    Given path '/admin/forecasts/cities/locationToUpdate'
    And request { "city": "locationForDelete", "country": "Polska", "latitude": 50.42, "longitude": 34.12 }
    When method PUT
    Then status 200

  Scenario: Integration test for get cities
    * header Authorization =  call read('basic-auth.js') {user: #(user), password: #(password)}
    Given path '/admin/forecasts/countries/Polska/cities'
    When method GET
    Then status 200
    And match $ == [{"city": "Warszawa", "country": "Polska", "latitude": 50.22, "longitude": 33.12 },{"city": "locationForDelete", "country": "Polska", "latitude": 50.42, "longitude": 34.12}]

  Scenario: Integration test for delete city
    * header Authorization =  call read('basic-auth.js') {user: #(user), password: #(password)}
    Given path '/admin/forecasts/cities/locationForDelete'
    When method DELETE
    Then status 200

  Scenario: Integration test for get forecast
    * def tomorrowDate = db.getTomorrowDateToString()
    Given path 'forecasts/city/Warszawa'
    And param date = tomorrowDate
    When method GET
    Then status 200
    And match $ contains { "id": '#number', "city": "Warszawa", "temperature": 23.4}