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

  Scenario: Price at 16:00 on 14th June 2020
    Given param applicationDate = '2020-06-14T16:00:00Z'
    And param productId = productId
    And param brandId = brandId
    When method get
    Then status 200
    And match response[0].price == 25.45
    And match response[0].priceList == 2
    And assert response.length == 1
 
  Scenario: Price at 21:00 on 14th June 2020
    Given param applicationDate = '2020-06-14T21:00:00Z'
    And param productId = productId
    And param brandId = brandId
    When method get
    Then status 200
    And match response[0].price == 35.50
    And match response[0].priceList == 1
    
  Scenario: Price at 10:00 on 15th June 2020
    Given param applicationDate = '2020-06-15T10:00:00Z'
    And param productId = productId
    And param brandId = brandId
    When method get
    Then status 200
    And match response[0].price == 30.50
    And match response[0].priceList == 3
    
  Scenario: Price at 21:00 on 16th June 2020
    Given param applicationDate = '2020-06-16T21:00:00Z'
    And param productId = productId
    And param brandId = brandId
    When method get
    Then status 200
    And match response[0].price == 38.95
    And match response[0].priceList == 4
    
  Scenario: No price found for parameters
    Given param applicationDate = '2019-01-01T00:00:00Z'
    And param productId = productId
    And param brandId = brandId
    When method get
    Then status 200
    And assert response.length == 0
    
 
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
    
  Scenario: Get all prices without filters
    When method get
    Then status 200
    And assert response.length > 0
    
  Scenario: Filter only by brandId
    Given param brandId = brandId
    When method get
    Then status 200
    And match each response[*].brandId == brandId
    
  Scenario: Filter only by productId
    Given param productId = productId
    When method get
    Then status 200
    And match each response[*].productId == productId
    
  Scenario: Filter only by applicationDate
    Given param applicationDate = '2020-06-14T16:00:00Z'
    When method get
    Then status 200
    And assert response.length > 0
    And match each response[*].startDate != null
    And match each response[*].endDate != null
    