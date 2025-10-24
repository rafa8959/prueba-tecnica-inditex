Feature: Get Applicable Prices API

  Background:
    * url 'http://localhost:8080/v1/prices'
    * def productId = 35455
    * def brandId = 1

  Scenario: Price at 10:00 on 14th June 2020
    Given param applicationDate = '2020-06-14T10:00:00Z'
    And param productId = productId
    And param brandId = brandId
    When method get
    Then status 200
    And match response[0].price == 35.50
    And match response[0].priceList == 1
    And match response[0].applied == true

  Scenario: Price at 16:00 on 14th June 2020
    Given param applicationDate = '2020-06-14T16:00:00Z'
    And param productId = productId
    And param brandId = brandId
    When method get
    Then status 200
    And match response[0].price == 25.45
    And match response[0].priceList == 2
    And match response[0].applied == true
    And match response[1].applied == false
 
  Scenario: Price at 21:00 on 14th June 2020
    Given param applicationDate = '2020-06-14T21:00:00Z'
    And param productId = productId
    And param brandId = brandId
    When method get
    Then status 200
    And match response[0].price == 35.50
    And match response[0].priceList == 1
    And match response[0].applied == true
    
  Scenario: Price at 10:00 on 15th June 2020
    Given param applicationDate = '2020-06-15T10:00:00Z'
    And param productId = productId
    And param brandId = brandId
    When method get
    Then status 200
    And match response[0].price == 30.50
    And match response[0].priceList == 3
    And match response[0].applied == true
    
  Scenario: Price at 21:00 on 16th June 2020
    Given param applicationDate = '2020-06-16T21:00:00Z'
    And param productId = productId
    And param brandId = brandId
    When method get
    Then status 200
    And match response[0].price == 38.95
    And match response[0].priceList == 4
    And match response[0].applied == true
    
  Scenario: No price found for parameters
    Given param applicationDate = '2019-01-01T00:00:00Z'
    And param productId = productId
    And param brandId = brandId
    When method get
    Then status 404
    And match response.message == "No price found for given parameters"
    
 
  Scenario: Invalid request parameters (wrong date format)
    Given param applicationDate = 'invalid-date'
    And param productId = productId
    And param brandId = brandId
    When method get
    Then status 400
    And match response.message == "Invalid request parameters"
    
  Scenario: Invalid request parameters (wrong brandId format)
    Given param applicationDate = '2019-01-01T00:00:00Z'
    And param productId = productId
    And param brandId = 'invalid-brand'
    When method get
    Then status 400
    And match response.message == "Invalid request parameters"
    
  Scenario: Invalid request parameters (wrong productId format)
    Given param applicationDate = '2019-01-01T00:00:00Z'
    And param productId = 'inval-id-product'
    And param brandId = brandId
    When method get
    Then status 400
    And match response.message == "Invalid request parameters"