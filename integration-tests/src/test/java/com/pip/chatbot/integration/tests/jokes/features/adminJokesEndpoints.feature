Feature: Admin Jokes Endpoints

  Background:
    * url baseUrl
    * def wrongAuthorization = callonce read('basic-auth.js') {user: #(user), password: #(incorrectPassword)}
    * def correctAuthorization = call read('basic-auth.js') {user: #(user), password: #(password)}

    * header Authorization =  correctAuthorization
    * def DbUtils = Java.type('com.pip.chatbot.integration.tests.jokes.JokesDbUtils')
    * def db = new DbUtils(dbConfig)
    * db.clearDb()
    * db.insertCategory()
    * def joke = db.insertJoke()
    * def createTestJoke = read('classpath:jokes/jokeForCreateTest.json')
    * configure afterScenario =
      """
      function(){
      db.clearDb()
      }
      """

  Scenario: Get joke by id
    Given path '/admin/jokes', joke.id
    When method GET
    Then status 200
    And match response == {id: #(joke.id), joke:  #(joke.joke), category: #(joke.category), confirmed: true}


  Scenario: Get all jokes
    Given path '/admin/jokes'
    When method GET
    Then status 200
    And match each response == {id: #(joke.id), joke:  #(joke.joke), category: #(joke.category), confirmed: true}


  Scenario: Create joke
    Given path '/admin/jokes'
    And request createTestJoke
    When method POST
    Then status 200
    And match response == {id: '#number', joke: #(createTestJoke.joke), category: #(createTestJoke.category), confirmed: true}

    * header Authorization =  correctAuthorization
    Given path '/admin/jokes', response.id
    When method GET
    Then status 200
    And match response == {id: #(response.id), joke: #(createTestJoke.joke), category: #(createTestJoke.category), confirmed: true}


  Scenario: Update joke
    Given path '/admin/jokes'
    And createTestJoke.id = joke.id
    And request createTestJoke
    When method PUT
    Then status 200
    And match response == {id: #(joke.id), joke: #(createTestJoke.joke), category: #(joke.category), confirmed: #(joke.confirmed)}

    * header Authorization =  correctAuthorization
    Given path '/admin/jokes', joke.id
    When method GET
    Then status 200
    And match response == {id: #(joke.id), joke: #(createTestJoke.joke), category: #(joke.category), confirmed: #(joke.confirmed)}

  Scenario: Delete joke by id
    Given path '/admin/jokes', joke.id
    When method DELETE
    Then status 200

    * header Authorization =  correctAuthorization
    Given path '/admin/jokes', joke.id
    When method GET
    Then status 400

  Scenario: Get joke by id with wrong authorization
    * header Authorization = wrongAuthorization
    Given path '/admin/jokes', joke.id
    When method GET
    Then status 401

  Scenario: Get all jokes with wrong authorization
    * header Authorization = wrongAuthorization
    Given path '/admin/jokes'
    When method GET
    Then status 401

  Scenario: Create joke with wrong authorization
    * header Authorization = wrongAuthorization
    Given path '/admin/jokes'
    And request createTestJoke
    When method POST
    Then status 401

  Scenario: Update joke with wrong authorization
    * header Authorization = wrongAuthorization
    Given path '/admin/jokes'
    And createTestJoke.id = joke.id
    And request createTestJoke
    When method PUT
    Then status 401

  Scenario: Delete joke by id with wrong authorization
    * header Authorization = wrongAuthorization
    Given path '/admin/jokes', joke.id
    When method DELETE
    Then status 401
