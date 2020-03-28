Feature: Admin login demand authentication

Background:
    * def loginUrl = baseUrl + '/admin/login'
    * def authorizationHeader = callonce read('basic-auth.js') {user: '#(user)', password: '#(password)'}

Scenario: Returns 401 without credentials
    Given url loginUrl
    When method GET
    Then status 401

Scenario: Login as admin
    * header Authorization = authorizationHeader
    Given url loginUrl
    When method GET
    Then status 200
    And match $ == {}



