Feature: Admin Authorization

  Background:
    * url baseUrl

  Scenario: Integration test for admin login endpoint with correct response
    * header Authorization =  call read('basic-auth.js') {user: #(user), password: #(password)}
      Given path '/admin/login'
      When method GET
      Then status 200



  Scenario: Integration test for admin login endpoint with incorrect response
    * header Authorization = call read('basic-auth.js') {user: #(user), password: #(incorrectPassword)}
    Given path '/admin/login'
    When method GET
    Then status 401
