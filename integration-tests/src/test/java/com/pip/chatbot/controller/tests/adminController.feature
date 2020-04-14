Feature: Admin Controller

  Background:
    * call read('./../karate-config.js')
    * url baseUrl

  Scenario: Testing correct Authorization Endpoint
    * header Authorization = header
      Given path '/admin/login'
      When method GET
      Then status 200



  Scenario: Testing unCorrect Authorization Endpoint
    * header Authorization = unCorrectHeader
    Given path '/admin/login'
    When method GET
    Then status 401
