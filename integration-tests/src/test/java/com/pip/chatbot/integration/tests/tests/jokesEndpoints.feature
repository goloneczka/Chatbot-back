Feature: Jokes Endpoints

  Background:
    * url baseUrl
    * def DbUtils = Java.type('com.pip.chatbot.integration.tests.utils.DbUtils')
    * def db = new DbUtils(dbConfig)

    * db.update("DELETE FROM jokes.mark");
    * db.update("DELETE FROM jokes.category")

    * db.update("INSERT INTO jokes.category(category,is_confirmed) VALUES(" + testCategory.category + "," + testCategory.isConfirmed + ")")
    * db.update("INSERT INTO jokes.joke(category,joke,is_confirmed) VALUES(" + testJoke.category + "," + testJoke.joke + "," + testJoke.isConfirmed + ")")
    * def joke = db.readRow("SELECT * FROM jokes.joke WHERE joke=" + testJoke.joke + " AND category=" + testJoke.category)

    * db.update("INSERT INTO jokes.mark(joke_id, mark) VALUES(" + joke.id + "," + testMark.mark + ")")
    * def mark = db.readRow("SELECT * FROM jokes.mark WHERE joke_id=" + joke.id)

    * configure afterScenario =
      """
      function(){
      db.update("DELETE FROM jokes.mark");
      db.update("DELETE FROM jokes.category")
      }
      """

  Scenario: Get random joke test
    Given path '/jokes/random'
    When method GET
    Then status 200
    And match response == {id: '#number', joke: '#string', category: '#string', confirmed: '#boolean'}

  Scenario: Get random joke by category test
    Given path '/jokes/random',joke.category
    When method GET
    Then status 200
    And match response == {id: #(joke.id), joke: #(joke.joke), category: #(joke.category), confirmed: #(joke.is_confirmed)}


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
    And match response == {jokeId: #(mark.joke_id), mark: #(mark.mark)}

  Scenario: Create mark for joke
    Given path '/jokes/rate'
    And request {jokeId: #(joke.id), mark: #(createMarkTest.mark)}
    When method POST
    Then status 200
    And match response == {jokeId: #(joke.id), mark: #(createMarkTest.mark)}

  Scenario: Get all categories
    Given path '/jokes/categories'
    When method GET
    Then status 200
    And match each response == {"category": '#string', 'confirmed': '#boolean'}