Feature: Stock Api

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
    * json symbol = read('classpath:fortune/symbol.json')
    * json stock = read('classpath:fortune/stock.json')
    * def predictionSymbol = callonce db.insertPrediction()

    * configure afterFeature =
    """
    function(){
      db.clearDatabase();
    }
    """

  Scenario: get stock for day
    Given path "stocks", symbol.symbol
    And param dateParam = "2020-05-04"
    When method GET
    Then status 200
    And match response == { "id": 1, "symbol": "symbol", "date":[2020,5,4],"value": 1.1078, "historical": true}


  Scenario: get stock for period
    Given path "stocks/period", symbol.symbol
    And param startDateParam = "2020-05-03"
    And param endDateParam = "2020-05-06"
    When method GET
    Then status 200
    And match response == [{"id": 1, "symbol": "symbol", "date":[2020,5,4],"value": 1.1078, "historical": true}]

  Scenario: get prediction for symbol
    Given path "stocks/prediction", symbol.symbol
    When method GET
    Then status 200
    And match response == [{ "id": 2, "symbol": "symbol", "date": [2222,5,4] ,"value": 1.1078, "historical": false}]
