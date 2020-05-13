Feature: Jokes Endpoints

  Background:
    * url baseUrl
    * header Authorization =  call read('basic-auth.js') {user: #(user), password: #(password)}
    * def DbUtils = Java.type('com.pip.chatbot.integration.tests.utils.DbUtils')
    * def db = new DbUtils(dbConfig)

    * db.update("DELETE FROM jokes.category")
    * db.update("INSERT INTO jokes.category(category,is_confirmed) VALUES(" + testCategory.category + "," + testCategory.isConfirmed + ")")
    * db.update("INSERT INTO jokes.joke(category,joke,is_confirmed) VALUES(" + testJoke.category + "," + testJoke.joke + "," + testJoke.isConfirmed + ")")
    * def joke = db.readRow("SELECT * FROM jokes.joke WHERE joke=" + testJoke.joke + " AND category=" + testJoke.category)

    * configure afterScenario =
      """
      function(){
      db.update("DELETE FROM jokes.category")
      }
      """

  Scenario: Get admin joke by id test
    Given path '/admin/jokes', joke.id
    When method GET
    Then status 200
    And match response == {id: #(joke.id), joke:  #(joke.joke), category: #(joke.category), confirmed: #(joke.is_confirmed)}


  Scenario: Get all admin jokes
    Given path '/admin/jokes'
    When method GET
    Then status 200
    And match each response == {id: '#number', joke:  '#string', category: '#string', confirmed: '#boolean'}


  Scenario: Create admin joke test
    Given path '/admin/jokes'
    And request createTestJoke
    When method POST
    Then status 200
    And match response == {id: '#number', joke: #(createTestJoke.joke), category: #(createTestJoke.category), confirmed: #(createTestJoke.isConfirmed)}

    * header Authorization =  call read('basic-auth.js') {user: #(user), password: #(password)}
    Given path '/admin/jokes', response.id
    When method GET
    Then status 200


  Scenario: Update admin joke test
    Given path '/admin/jokes'
    And createTestJoke.id = joke.id
    And request createTestJoke
    When method PUT
    Then status 200
    And match response == {id: #(joke.id), joke: #(createTestJoke.joke), category: #(joke.category), confirmed: #(createTestJoke.isConfirmed)}

    * header Authorization =  call read('basic-auth.js') {user: #(user), password: #(password)}
    Given path '/admin/jokes', joke.id
    When method GET
    Then status 200
    And match response == {id: #(joke.id), joke: #(createTestJoke.joke), category: #(joke.category), confirmed: #(joke.is_confirmed)}

  Scenario: Delete joke by id
    Given path '/admin/jokes', joke.id
    When method DELETE
    Then status 200

    * header Authorization =  call read('basic-auth.js') {user: #(user), password: #(password)}
    Given path '/admin/jokes', joke.id
    When method GET
    Then status 500
