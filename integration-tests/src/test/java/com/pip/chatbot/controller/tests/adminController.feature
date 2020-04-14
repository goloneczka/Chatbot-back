Feature: Admin Controller

  Background:
    * url baseUrl

  Scenario: Testing correct Authorization Endpoint
    * header Authorization =  call read('basic-auth.js') {user: #(user), password: #(password)}
      Given path '/admin/login'
      When method GET
      Then status 200



  Scenario: Testing unCorrect Authorization Endpoint
    * header Authorization = call read('basic-auth.js') {user: #(user), password: #(uncorrectPassword)}
    Given path '/admin/login'
    When method GET
    Then status 401
