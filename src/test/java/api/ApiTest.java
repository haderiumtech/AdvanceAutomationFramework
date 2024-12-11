// src/test/java/tests/ApiTest.java
package api;

import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;
import utils.ApiUtils;

public class ApiTest {

    @Test
    public void testGetPosts() {
        // Example of a GET request
        Response response = ApiUtils.get("/posts");
        
        // Validate response status
        Assert.assertEquals(response.getStatusCode(), 200, "Status code should be 200");
        
        // Validate response body (example check)
        Assert.assertTrue(response.jsonPath().getList("$").size() > 0, "Post list should not be empty");
    }

    @Test
    public void testCreatePost() {
        // Example payload for a POST request
        String requestBody = """
                {
                    "title": "foo",
                    "body": "bar",
                    "userId": 1
                }
                """;

        Response response = ApiUtils.post("/posts", requestBody);
        
        // Validate response status
        Assert.assertEquals(response.getStatusCode(), 201, "Status code should be 201 for a successful post creation");
        
        // Validate response body contains the correct data
        Assert.assertEquals(response.jsonPath().getString("title"), "foo");
        Assert.assertEquals(response.jsonPath().getString("body"), "bar");
    }
}
