package lib;

import io.qameta.allure.Step;
import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.response.Response;
import java.util.Map;

import static io.restassured.RestAssured.given;

public class ApiCoreRequests {

    @Step("Make a GET-request without token and auth cookie")
    public Response makeGetRequestWithoutTokenCookie(String url) {
        return given()
                .filter(new AllureRestAssured())
                .get(url)
                .andReturn();
    }


    @Step("Make a GET-request with token and auth cookie")
    public Response makeGetRequest(String url, String token, String cookie) {
        return given()
                .filter(new AllureRestAssured())
                .header("x-csrf-token", token)
                .cookie("auth_sid", cookie)
                .get(url)
                .andReturn();
    }


    @Step("Make a POST-request")
    public Response makePostRequest(String url, Map<String, String> userData) {
        return given()
                .filter(new AllureRestAssured())
                .body(userData)
                .post(url)
                .andReturn();
    }

    @Step("Make a PUT-request without token and cookie")
    public Response makePutRequestWithoutAutorization(String url, Map<String, String> userData) {
        return given()
                .filter(new AllureRestAssured())
                .body(userData)
                .put(url)
                .andReturn();
    }

    @Step("Make a PUT-request")
    public Response makePutRequest(String url, String token, String cookie, Map<String, String> userData) {
        return given()
                .filter(new AllureRestAssured())
                .header("x-csrf-token", token)
                .cookie("auth_sid", cookie)
                .body(userData)
                .put(url)
                .andReturn();
    }

    @Step("Make a DELETE-request")
    public Response makeDeleteRequest(String url, String token, String cookie) {
        return given()
                .filter(new AllureRestAssured())
                .header("x-csrf-token", token)
                .cookie("auth_sid", cookie)
                .delete(url)
                .andReturn();
    }


}

