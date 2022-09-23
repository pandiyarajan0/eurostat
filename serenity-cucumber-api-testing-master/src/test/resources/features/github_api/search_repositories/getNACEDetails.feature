Feature: Verify the NACE Details API

  @Regression @unitTest
  Scenario Outline: To verify the NACE details API response with actual test data
    Given passing the "getNaceDetails" url parameters
    When get the getNaceDetails API with <orderId>
    Then verify the response status code and content type
    And verify the response with actual data in csv file
    
    Examples:
      | orderId | 
      | 398481  | 
     
      
