@ignore
Feature: Database cleaning
  Scenario: clean the database after restauration feature
    * def DbUtils = Java.type('com.pip.chatbot.integration.tests.WeatherDbUtils')
    * def db = new DbUtils()
    * db.clearWeatherData()