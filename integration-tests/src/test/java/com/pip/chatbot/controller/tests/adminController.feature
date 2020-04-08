Feature: Admin Controller

  Background:
    * url 'http://localhost:9090'
    * def authorizationHeader = read('./karateAuthorization.json')

  Scenario: Testing correct Authorization Endpoint
    * configure headers = { Authorization: '#(authorizationHeader.correctAuthorization)' }
      Given path '/admin/login'
      When method GET
      Then status 200



  Scenario: Testing unCorrect Authorization Endpoint
    * configure headers = { Authorization: '#(authorizationHeader.unCorrectAuthorization)' }
    Given path '/admin/login'
    When method GET
    Then status 401
