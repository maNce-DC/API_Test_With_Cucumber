package com.apitest.stepdefinitions;

import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.*;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.junit.Assert;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

public class APIStepDefinitions {

    private RequestSpecification request;
    private Response response;
    private Map<String, Object> requestData;

    @Given("the API base URL is {string}")
    public void setBaseURL(String url) {
        RestAssured.baseURI = url;       // https://reqres.in
        RestAssured.basePath = "/api";
        RestAssured.useRelaxedHTTPSValidation();

        String apiKey = System.getenv("REQRES_API_KEY");
        if (apiKey == null || apiKey.isBlank()) {
            apiKey = "reqres-free-v1";
        }

        this.request = given()
                .accept("application/json")
                .contentType("application/json")
                .header("x-api-key", apiKey)   // ← ერთი ცალი!
                .log().all();
    }




    @When("I send a GET request to {string}")
    public void sendGetRequest(String endpoint) {
        response = request.when().get(endpoint);
        log("GET", endpoint);
    }


    @When("I send a POST request to {string} with the user data")
    public void sendPostRequest(String endpoint) {
        response = request.body(requestData).when().post(endpoint);
        log("POST", endpoint);
        System.out.println("Request Body: " + requestData);
    }


    @When("I send a PUT request to {string} with the user data")
    public void sendPutRequest(String endpoint) {
        response = request.body(requestData).when().put(endpoint);
        log("PUT", endpoint);
        System.out.println("Request Body: " + requestData);
    }

    @When("I send a PATCH request to {string} with the user data")
    public void sendPatchRequest(String endpoint) {
        response = request.body(requestData).when().patch(endpoint);
        log("PATCH", endpoint);
        System.out.println("Request Body: " + requestData);
    }

    @When("I send a DELETE request to {string}")
    public void sendDeleteRequest(String endpoint) {
        response = request.when().delete(endpoint);
        log("DELETE", endpoint);
    }

    // ----- Log full responses
    private void log(String method, String endpoint) {
        System.out.println(method + " Request to: " + RestAssured.baseURI + RestAssured.basePath + endpoint);
        response.then().log().all();
    }

    // ----- Assertions and data methods

    @Given("I have user data:")
    public void setUserData(DataTable dataTable) {
        requestData = toMap(dataTable);
        System.out.println("User data set: " + requestData);
    }
    @Given("I have invalid user data:") public void setInvalidUserData(DataTable t){ setUserData(t); }
    @Given("I have updated user data:") public void setUpdatedUserData(DataTable t){ setUserData(t); }
    @Given("I have partial user data:") public void setPartialUserData(DataTable t){ setUserData(t); }

    private Map<String, Object> toMap(DataTable table) {
        Map<String, Object> map = new HashMap<>();
        for (List<String> row : table.asLists()) map.put(row.get(0), row.get(1));
        return map;
    }

    @Then("the response status code should be {int}")
    public void validateStatusCode(int expectedStatusCode) {
        int actualStatusCode = response.getStatusCode();
        System.out.println("Expected Status Code: " + expectedStatusCode + ", Actual: " + actualStatusCode);
        Assert.assertEquals("Status code mismatch", expectedStatusCode, actualStatusCode);
    }

    @Then("the response should contain users list")
    public void validateUsersListResponse() {
        response.then()
                .body("data", notNullValue())
                .body("data.size()", greaterThan(0))
                .body("data[0].id", notNullValue())
                .body("data[0].email", notNullValue())
                .body("data[0].first_name", notNullValue())
                .body("data[0].last_name", notNullValue());
    }

    @Then("the response should contain user with id {int}")
    public void validateUserWithId(int userId) {
        response.then()
                .body("data.id", equalTo(userId))
                .body("data.email", notNullValue())
                .body("data.first_name", notNullValue())
                .body("data.last_name", notNullValue());
    }

    @Then("the user should have {string} as email")
    public void validateUserEmail(String expectedEmail) {
        response.then().body("data.email", equalTo(expectedEmail));
    }

    @Then("the response should contain the created user data")
    public void validateCreatedUserData() {
        response.then()
                .body("name", equalTo(requestData.get("name")))
                .body("job", equalTo(requestData.get("job")))
                .body("id", notNullValue());
    }

    @Then("the response should have {string} field")
    public void validateFieldExists(String fieldName) {
        response.then().body(fieldName, notNullValue());
    }

    @Then("the response should contain the updated user data")
    public void validateUpdatedUserData() {
        response.then()
                .body("name", equalTo(requestData.get("name")))
                .body("job", equalTo(requestData.get("job")));
    }

    @Then("the response body should be empty")
    public void validateEmptyResponseBody() {
        String responseBody = response.getBody().asString();
        Assert.assertTrue("Response body should be empty",
                responseBody == null || responseBody.trim().isEmpty());
    }

    @Then("the response time should be less than {int}ms")
    public void validateResponseTime(long maxTime) {
        long actualTime = response.getTime();
        System.out.println("Response time: " + actualTime + "ms (max allowed: " + maxTime + "ms)");
        Assert.assertTrue("Response time " + actualTime + "ms exceeds maximum " + maxTime + "ms",
                actualTime < maxTime);
    }

    @Then("the response should contain resources list")
    public void validateResourcesListResponse() {
        response.then()
                .body("data", notNullValue())
                .body("data.size()", greaterThan(0))
                .body("data[0].id", notNullValue())
                .body("data[0].name", notNullValue())
                .body("data[0].year", notNullValue())
                .body("data[0].color", notNullValue())
                .body("data[0].pantone_value", notNullValue());
    }

    @Then("each resource should have required fields")
    public void validateResourceFields() {
        response.then()
                .body("data.findAll { it.id == null }", hasSize(0))
                .body("data.findAll { it.name == null }", hasSize(0))
                .body("data.findAll { it.year == null }", hasSize(0))
                .body("data.findAll { it.color == null }", hasSize(0));
    }

    @Then("the response should contain resource with id {int}")
    public void the_response_should_contain_resource_with_id(int resourceId) {
        response.then()
                .body("data.id", equalTo(resourceId))
                .body("data.name", notNullValue())
                .body("data.year", notNullValue())
                .body("data.color", notNullValue())
                .body("data.pantone_value", notNullValue());
    }
}
