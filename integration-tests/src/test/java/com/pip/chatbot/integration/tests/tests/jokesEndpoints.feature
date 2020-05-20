Feature: Jokes Endpoints

  Background:
    * url baseUrl
    * def DbUtils = Java.type('com.pip.chatbot.integration.tests.utils.JokesDbUtils')
    * def db = new DbUtils(dbConfig)
    * db.clearDb()
    * db.insertCategory()
    * def joke = db.insertJoke()
    * def mark = db.insertMark(joke.id)
    * def createTestJoke = read('classpath:jokes/jokeForCreateTest.json')
    * def createTestMark = read('classpath:jokes/markForCreateTest.json')
    * configure afterScenario =
      """
      function(){
        db.clearDb()
      }
      """

  Scenario: Get random joke test
    Given path '/jokes/random'
    When method GET
    Then status 200
    And match response == {id: '#number', joke: '#string', category: '#string', confirmed: true}

  Scenario: Get random joke by category test
    Given path '/jokes/random',joke.category
    When method GET
    Then status 200
    And match response == {id: #(joke.id), joke: #(joke.joke), category: #(joke.category), confirmed: true}

  Scenario: Create joke test
    Given path '/jokes'
    And request createTestJoke
    When method POST
    Then status 200
    And match response == {id: '#notnull', joke: #(createTestJoke.joke), category: #(createTestJoke.category), confirmed: false}

  Scenario: Get mark for joke
    Given path '/jokes/rate', joke.id
    When method GET
    Then status 200
    And match response == {jokeId: #(mark.jokeId), mark: #(mark.mark)}

  Scenario: Create mark for joke
    Given path '/jokes/rate'
    And request {jokeId: #(joke.id), mark: #(createTestMark.mark)}
    When method POST
    Then status 200
    And match response == {jokeId: #(joke.id), mark: #(createTestMark.mark)}

  Scenario: Get all categories
    Given path '/jokes/categories'
    When method GET
    Then status 200
    And match each response == {"category": '#string', 'confirmed': '#boolean'}