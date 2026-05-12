Feature: Resources API Testing
  As a user of the API
  I want to retrieve resource information
  So that I can access available resources

  Background:
    Given the API base URL is "https://reqres.in"

  @smoke @resources
  Scenario: Get all resources successfully
    When I send a GET request to "/unknown"
    Then the response status code should be 200
    And the response should contain resources list
    And each resource should have required fields

  @smoke @resources
  Scenario: Get resource by ID successfully
    When I send a GET request to "/unknown/2"
    Then the response status code should be 200
    And the response should contain resource with id 2

  @negative @resources
  Scenario: Get non-existent resource
    When I send a GET request to "/unknown/999"
    Then the response status code should be 404
