package utils;

import io.restassured.RestAssured;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

public class ApiUtils {

    static {
        RestAssured.baseURI = "https://jsonplaceholder.typicode.com"; // Example base URI
    }

    public static Response get(String endpoint) {
        LoggerUtils.logInfo("Sending GET request to: " + endpoint);
        return given()
                .when()
                .get(endpoint)
                .then()
                .extract()
                .response();
    }

    public static Response post(String endpoint, Object body) {
        LoggerUtils.logInfo("Sending POST request to: " + endpoint);
        return given()
                .header("Content-Type", "application/json")
                .body(body)
                .when()
                .post(endpoint)
                .then()
                .extract()
                .response();
    }

    public static Response put(String endpoint, Object body) {
        LoggerUtils.logInfo("Sending PUT request to: " + endpoint);
        return given()
                .header("Content-Type", "application/json")
                .body(body)
                .when()
                .put(endpoint)
                .then()
                .extract()
                .response();
    }

    public static Response delete(String endpoint) {
        LoggerUtils.logInfo("Sending DELETE request to: " + endpoint);
        return given()
                .when()
                .delete(endpoint)
                .then()
                .extract()
                .response();
    }
}