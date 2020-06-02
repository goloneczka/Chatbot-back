Feature:  Admin Jokes Categories Endpoints

  Background:
    * url baseUrl
    * def DbUtils = Java.type('com.pip.chatbot.integration.tests.jokes.JokesDbUtils')
    * def db = new DbUtils(dbConfig)
    * header Authorization =  callonce read('basic-auth.js') {user: #(user), password: #(password)}
    * db.clearDb()
    * def createTestCategory = read('classpath:jokes/categoryForCreateTest.json')
    * def category = db.insertCategory()
    * def joke = db.insertJoke()
    * def mark = db.insertMark(joke.id)
    * configure afterScenario =
      """
      function(){
      db.clearDb()
      }
      """

  Scenario: Get all admin categories
    Given path '/admin/jokes/categories'
    When method GET
    Then status 200
    And match each response == {category: #(category.category), confirmed: true}


#  Scenario: Create admin category test
#    Given path '/admin/jokes/categories'
#    And request createTestCategory
#    When method POST
#    Then status 200
#    And match response == {category: #(createTestCategory.category), confirmed: #(createTestCategory.isConfirmed)}

  Scenario: Update admin category test
    Given path '/admin/jokes/categories', category.category
    And request createTestCategory
    When method PUT
    Then status 200
    And match response == {category: #(createTestCategory.category), confirmed: #(createTestCategory.confirmed)}

  Scenario: Delete category test
    Given path '/admin/jokes/categories', category.category
    When method DELETE
    Then status 200

    * header Authorization =  call read('basic-auth.js') {user: #(user), password: #(password)}
    Given path '/admin/jokes/categories'
    When method GET
    Then status 200
    And match response !contains category
