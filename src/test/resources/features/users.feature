Feature: Users API Testing
  As a user of the API
  I want to perform CRUD operations on users
  So that I can manage user data

  Background:
    Given the API base URL is "https://reqres.in"

  @smoke @get
  Scenario: Get all users successfully
    When I send a GET request to "/users"
    Then the response status code should be 200
    And the response should contain users list
    And the response time should be less than 3000ms

  @smoke @get
  Scenario: Get user by ID successfully
    When I send a GET request to "/users/2"
    Then the response status code should be 200
    And the response should contain user with id 2
    And the user should have "janet.weaver@reqres.in" as email

  @negative @get
  Scenario: Get non-existent user
    When I send a GET request to "/users/999"
    Then the response status code should be 404

  @smoke @post
  Scenario: Create a new user successfully
    Given I have user data:
      | name | John Doe        |
      | job  | Software Tester |
    When I send a POST request to "/users" with the user data
    Then the response status code should be 201
    And the response should contain the created user data
    And the response should have "createdAt" field

  @negative @post
  Scenario: Create user with invalid data
    Given I have invalid user data:
      | name | |
      | job  | |
    When I send a POST request to "/users" with the user data
    Then the response status code should be 201
    # შენიშვნა: reqres.in თითქმის ყველაფერს იღებს, რეალ API-ში ეს 400 იქნებოდა

  @smoke @put
  Scenario: Update user successfully
    Given I have updated user data:
      | name | John Updated  |
      | job  | Senior Tester |
    When I send a PUT request to "/users/2" with the user data
    Then the response status code should be 200
    And the response should contain the updated user data
    And the response should have "updatedAt" field

  @smoke @patch
  Scenario: Partially update user successfully
    Given I have partial user data:
      | job | Lead Tester |
    When I send a PATCH request to "/users/2" with the user data
    Then the response status code should be 200
    And the response should have "updatedAt" field

  @smoke @delete
  Scenario: Delete user successfully
    When I send a DELETE request to "/users/2"
    Then the response status code should be 204
    And the response body should be empty
