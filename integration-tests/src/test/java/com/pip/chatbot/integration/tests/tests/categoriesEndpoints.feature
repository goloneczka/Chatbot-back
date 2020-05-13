Feature: Jokes Endpoints

  Background:
    * url baseUrl
    * def DbUtils = Java.type('com.pip.chatbot.integration.tests.utils.DbUtils')
    * def db = new DbUtils(dbConfig)
    * header Authorization =  call read('basic-auth.js') {user: #(user), password: #(password)}
    * db.update("DELETE FROM jokes.category")

    * db.update("INSERT INTO jokes.category(category,is_confirmed) VALUES(" + testCategory.category + "," + testCategory.isConfirmed + ")")
    * def category = db.readRow("SELECT * FROM jokes.category WHERE category=" + testCategory.category)

    * db.update("INSERT INTO jokes.joke(category,joke,is_confirmed) VALUES(" + testJoke.category + "," + testJoke.joke + "," + testJoke.isConfirmed + ")")
    * def joke = db.readRow("SELECT * FROM jokes.joke WHERE joke=" + testJoke.joke + " AND category=" + testJoke.category)

    * configure afterScenario =
      """
      function(){
      db.update("DELETE FROM jokes.category")
      }
      """

  Scenario: Get all admin categories
    Given path '/admin/jokes/categories'
    When method GET
    Then status 200
    And match each response == {category: '#string', confirmed: '#boolean'}


  Scenario: Create admin category test
    Given path '/admin/jokes/categories'
    And request createTestCategory
    When method POST
    Then status 200
    And match response == {category: #(createTestCategory.category), confirmed: #(createTestCategory.isConfirmed)}

  Scenario: Update admin category test
    Given path '/admin/jokes/categories', category.category
    And request createTestCategory
    When method PUT
    Then status 200
    And match response == {category: #(createTestCategory.category), confirmed: #(createTestCategory.isConfirmed)}

  Scenario: Delete category test
    Given path '/admin/jokes/categories', category.category
    When method DELETE
    Then status 200

    * header Authorization =  call read('basic-auth.js') {user: #(user), password: #(password)}
    Given path '/admin/jokes/categories'
    When method GET
    Then status 200
    And match response !contains category
