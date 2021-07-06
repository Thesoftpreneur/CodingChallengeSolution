package com.softprenuer.stepdefinitions;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.json.JSONObject;
import org.junit.jupiter.api.Assertions;

public class CommentsAPI {
    
    private String gistId;
    private String commentBody;
    private String commentId;
    private Response response;


    @Given("a Gist id: {string}")
    public void aGistId(String gId) {
        gistId=gId;
    }

    @Given("a Comment body: {string}")
    public void aCommentBody(String cBody) {
        commentBody=cBody;
    }

    @Given("a Comment id: {string}")
    public void aCommentId(String cId) {
        commentId=cId;
    }

    private RequestSpecification requestBase() {
        RestAssured.baseURI = "https://api.github.com";
        return RestAssured.given()
                .contentType("application/json")
                .accept("application/vnd.github.v3+json");
    }

    private String prepareCommentBody() {
        JSONObject commentBodyJson = new JSONObject();
        commentBodyJson.put("body", commentBody);
        return commentBodyJson.toString();
    }

    private String getCommentIdFromOfResponse() {
        JsonPath json = response.jsonPath();
        return json.get("id").toString();
    }


    @When("a request listing all the comments is sent")
    public void aRequestListingAllTheCommentsIsSent() {

        response = requestBase()
                .pathParam("gistId", gistId)
//                .auth()
//                .oauth2(PropertiesManager.getProperty("token"))
                .when()
                .get("/gists/{gistId}/comments")
                .then()
                .log().all()
              //  .statusCode(200)
                .extract()
                .response();
    }

    @Then("{string} status code is returned")
    public void statusCodeIsReturned(String statusCode) {
        Assertions.assertEquals(statusCode, String.valueOf(response.getStatusCode()));
    }

    @When("a request creating a gist comment is sent")
    public void aRequestCreatingAGistCommentIsSent() {

        response = requestBase()
                .pathParam("gistId", gistId)
                .auth()
                .oauth2(PropertiesManager.getProperty("token"))
                .body(prepareCommentBody())
                .when()
                .post("/gists/{gistId}/comments")
                .then()
                .log().all()
                //.statusCode(201)
                .extract()
                .response();
    }

    @When("an unauthorized request creating a gist comment is sent")
    public void anUnauthorizedRequestCreatingAGistCommentIsSent() {

        response = requestBase()
                .pathParam("gistId", gistId)
                .body(prepareCommentBody())
                .when()
                .post("/gists/{gistId}/comments")
                .then()
                .log().all()
                //.statusCode(401)
                .extract()
                .response();
    }


    @When("a request listing a gist comment is sent")
    public void aRequestListingAGistCommentIsSent() {

        response = requestBase()
                .pathParam("gistId", gistId)
                .pathParam("commentId", commentId)
                .auth()
                .oauth2(PropertiesManager.getProperty("token"))
                .when()
                .get("/gists/{gistId}/comments/{commentId}")
                .then()
                .log().all()
                //.statusCode(200)
                .extract()
                .response();
    }

    @When("a request updating a gist comment is sent")
    public void aRequestUpdatingAGistCommentIsSent() {
        response = requestBase()
                .pathParam("gistId", gistId)
                .pathParam("commentId", commentId)
                .body(prepareCommentBody())
                .auth()
                .oauth2(PropertiesManager.getProperty("token"))
                .when()
                .patch("/gists/{gistId}/comments/{commentId}")
                .then()
                .log().all()
                //.statusCode(200)
                .extract()
                .response();
    }

    /*
     *  Warning:
     *  This Keyword removes the lastly requested
     *  gist comment
     */
    @When("a request removing a gist comment is sent")
    public void aRequestRemovingAGistCommentIsSent() {
        response = requestBase()
                .pathParam("gistId", gistId)
                .pathParam("commentId", getCommentIdFromOfResponse())
                .auth()
                .oauth2(PropertiesManager.getProperty("token"))
                .when()
                .delete("/gists/{gistId}/comments/{commentId}")
                .then()
                .log().all()
                //.statusCode(401)
                .extract()
                .response();
    }

    @Then("the comment body {string} is returned")
    public void theCommentBodyIsReturned(String cBody) {
        JsonPath json = response.jsonPath();
        Assertions.assertEquals(cBody, json.get("body").toString());
    }
}
