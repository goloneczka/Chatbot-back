Feature: Restaurants Api

  Background:
    * url baseUrl
    * def DbUtils = Java.type('com.pip.chatbot.integration.tests.foodDbUtils')
    * def db = new DbUtils()
    * def poland = "Polska"
    * def wwa = "Warszawa"
    * def plock = "Plock"
    * def ldz = "Lodz"
    * db.insertCountry(poland)
    * db.insertCity(poland, plock)
    * db.insertCity(poland, wwa)
    * db.insertCity(poland, ldz)

  Scenario: Integration test for food endpoints to get cities.
    Given path '/food/city'
    When method GET
    Then status 200
    And match $ == [{id:"#number", city:plock, country:poland}, {id:"#number",city:wwa, country:poland}, {id:"#number",city:ldz, country:poland} ]

#  Scenario: Integration test for food endpoints to get cities.
#    * def cityId = 3
#    Given path '/city/' + cityId + '/cuisine'
#    When method GET
#    Then status 200
#    And match $ == [{id:1, name:"chinol 1", address:"Tumska 3", city_id:3, average_users_rating: 3.9, phone_numbers:"123456789"}, {id:2, name:"chinol 2", address:"Tumska 9", city_id:3, average_users_rating: 3.9, phone_numbers:"123456780"}]

#  Scenario: Integration test for food endpoints to get rando restaurant in city.
#    * def cityId = 4
#    * def cuisine = "kebsiki"
#    Given path '/food/city/'+ cityId + '/cuisine/' + :cuisine/restaurant
#    When method GET
#    Then status 200
#    And match $ == [{id:3, city:"Plock", country:"polish"}, {id:4, city:"Wwa", country:"polish"} ]