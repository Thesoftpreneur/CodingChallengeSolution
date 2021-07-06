package com.softprenuer.stepdefinitions;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.internal.common.assertion.Assertion;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.json.JSONObject;
import org.junit.Assert;
import org.junit.jupiter.api.Assertions;

import java.io.*;
import java.util.Properties;

import static io.restassured.RestAssured.given;

public class GistsAPI {
    private String gistName;
    private String description;
    private String content;
    private String rsGistName;
    private String rsDescription;
    private String rsContent;
    private String id;
    private final String BASE_URL = "https://api.github.com";

    @Given("a gist name: {string}")
    public void an_open_browser(String gistName) {
        this.gistName=gistName;
    }

    @Given("a gist description: {string}")
    public void aGistDescription(String description) {
        this.description = description;
    }

    @Given("a gist content: {string}")
    public void aGistContent(String content) {
        this.content = content;
    }

    @When("a request is sent")
    public void aRequestIsSent() {

        JSONObject file = new JSONObject();
        file.put("content", content);

        JSONObject files = new JSONObject();
        files.put(gistName, file);

        JSONObject gist = new JSONObject();
        gist.put("files", files);
        gist.put("description", description);
        gist.put("public", Boolean.TRUE);

        Response postResponse = given()
                .auth()
                .oauth2(PropertiesManager.getProperty("token"))
                .contentType("application/json")
                .accept("application/vnd.github.v3+json")
                .body(gist.toString())
                .when()
                .post(BASE_URL + "/gists")
                .then()
                //.log().all()
                .statusCode(201)
                .extract()
                .response();

        id = postResponse.jsonPath().get("id").toString();

        Response getResponse = given()
                .auth()
                .oauth2(PropertiesManager.getProperty("token"))
                .contentType("application/json")
                .accept("application/vnd.github.v3+json")
                .when()
                .get(BASE_URL + "/gists/" + id)
                .then()
                //.log().all()
                .statusCode(200)
                .extract()
                .response();

        JsonPath json = getResponse.jsonPath();

        rsGistName = json.get("files."+gistName+".filename");
        rsDescription = json.get("description");
        rsContent = json.get("files."+gistName+".content");
    }

    @Then("the gist name is {string}")
    public void theGistNameIs(String expectedGistName) {
        Assertions.assertEquals(expectedGistName, rsGistName);
    }

    @Then("the gist description is {string}")
    public void theGistDescriptionIs(String expectedDescription) {
        Assertions.assertEquals(expectedDescription, rsDescription);
    }

    @Then("the gist content is {string}")
    public void theGistContentIs(String expectedContent) {
        Assertions.assertEquals(expectedContent, rsContent);
    }
}
