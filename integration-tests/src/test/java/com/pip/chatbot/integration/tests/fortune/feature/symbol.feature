Feature: Symbol Api

  Background:
    * url baseUrl
    * def initDatabase =
    """
    function() {
      var DbUtils = Java.type('com.pip.chatbot.integration.tests.fortune.FortuneDbUtils');
      var db = new DbUtils(dbConfig);
      db.initFortuneData();
     return db;
    }
    """
    * def db = callonce initDatabase

    * json symbols = read('classpath:fortune/symbol.json')

    * json symbolToCreate = read('classpath:fortune/symbolToCreate.json')

    * json symbolUpdated = read('classpath:fortune/symbolUpdated.json')

    * configure afterFeature =
    """
    function(){
      db.clearDatabase();
    }
    """

  Scenario: get Symbol
    Given path 'symbols/companies'
    When method GET
    Then status 200
    And match response == [{ "symbol": "symbol","name": "nameSymbol","currency": false}]

  Scenario: add symbol
    Given path 'symbols'
    And request symbolToCreate
    When method POST
    Then status 200
    And match response == {"symbol": "symbolToCreate","name": "nameSymbolToCreate","currency": false }

  Scenario: update symbol
    Given path 'symbols'
    And request symbolUpdated
    When method PUT
    Then status 200
    And match response == { "symbol": "symbol","name": "nameSymbolUpdated","currency": false}

  Scenario: delete symbol
    Given path 'symbols'
    And request "symbol"
    When method DELETE
    Then status 200
