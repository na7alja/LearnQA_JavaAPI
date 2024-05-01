package tests;

import io.qameta.allure.*;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import lib.ApiCoreRequests;
import lib.Assertions;
import lib.BaseTestCase;
import lib.DataGenerator;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

@Epic("Delete cases")
@Feature("Delete user data")
public class UserDeleteTest extends BaseTestCase {
    private final ApiCoreRequests apiCoreRequests = new ApiCoreRequests();

    @Description("This test tries to delete user data for user 2")
    @DisplayName("Test negative deleting user data for user 2")
    @Owner("Анчевская Наталья")
    @Issue("IS-1212")
    @TmsLinks({@TmsLink(value = "TL-100"), @TmsLink(value = "TL-101"), @TmsLink(value = "TL-102")})
    @Severity(value = SeverityLevel.BLOCKER)
    @Test
    public void testDeleteUserDataForUser2() {

        //LOGIN
        Map<String, String> authData = new HashMap<>();
        authData.put("email", "vinkotov@example.com");
        authData.put("password", "1234");

        Response responseGetAuth = apiCoreRequests.makePostRequest("https://playground.learnqa.ru/api/user/login", authData);

        //DELETE

        Response responseEditUser = apiCoreRequests.makeDeleteRequest(
                "https://playground.learnqa.ru/api/user/2",
                this.getHeader(responseGetAuth, "x-csrf-token"),
                this.getCookie(responseGetAuth, "auth_sid"));

        Assertions.assertResponseCodeEquals(responseEditUser, 400);
        Assertions.assertJsonByName(responseEditUser, "error", "Please, do not delete test users with ID 1, 2, 3, 4 or 5.");

    }

    @Description("This test delete user data successfully")
    @DisplayName("Test positive deleting user data when user login and authorise")
    @Owner("Анчевская Наталья")
    @Severity(value = SeverityLevel.CRITICAL)
    @Test
    public void testDeleteUserData() {

        //GENERATE USER
        Map<String, String> userData = DataGenerator.getRegistrationData();

        JsonPath responseCreateAuth = apiCoreRequests.makePostRequest("https://playground.learnqa.ru/api/user/", userData).jsonPath();

        String userId = responseCreateAuth.getString("id");

        //LOGIN
        Map<String, String> authData = new HashMap<>();
        authData.put("email", userData.get("email"));
        authData.put("password", userData.get("password"));

        Response responseGetAuth = apiCoreRequests.makePostRequest("https://playground.learnqa.ru/api/user/login", authData);

        //DELETE

        Response responseEditUser = apiCoreRequests.makeDeleteRequest(
                "https://playground.learnqa.ru/api/user/" + userId,
                this.getHeader(responseGetAuth, "x-csrf-token"),
                this.getCookie(responseGetAuth, "auth_sid"));

        Assertions.assertResponseCodeEquals(responseEditUser, 200);
        Assertions.assertJsonByName(responseEditUser, "success", "!");


        //GET
        Response responseUserData = apiCoreRequests.makeGetRequest(
                "https://playground.learnqa.ru/api/user/" + userId,
                this.getHeader(responseGetAuth, "x-csrf-token"),
                this.getCookie(responseGetAuth, "auth_sid"));

        Assertions.assertResponseTextEquals(responseUserData, "User not found");
    }


    @Description("This test tries to delete user data with auth. data by anther user")
    @DisplayName("Test negative deleting user data with auth. data by anther user")
    @Owner("Анчевская Наталья")
    @Severity(value = SeverityLevel.NORMAL)
    @Test
    public void testDeleteUserAuthByOtherUser() {
        //GENERATE USER1
        Map<String, String> userData1 = DataGenerator.getRegistrationData();

        apiCoreRequests.makePostRequest("https://playground.learnqa.ru/api/user/", userData1);

        //GENERATE USER2
        Map<String, String> userData2 = DataGenerator.getRegistrationData();

        JsonPath responseCreateAuth2 = apiCoreRequests.makePostRequest("https://playground.learnqa.ru/api/user/", userData2).jsonPath();

        String userId = responseCreateAuth2.getString("id");

        //LOGIN USER1
        Map<String, String> authData = new HashMap<>();
        authData.put("email", userData1.get("email"));
        authData.put("password", userData1.get("password"));

        Response responseGetAuth = apiCoreRequests.makePostRequest("https://playground.learnqa.ru/api/user/login", authData);

        //DELETE
        Response responseEditUser = apiCoreRequests.makeDeleteRequest(
                "https://playground.learnqa.ru/api/user/" + userId,
                this.getHeader(responseGetAuth, "x-csrf-token"),
                this.getCookie(responseGetAuth, "auth_sid"));

        Assertions.assertResponseCodeEquals(responseEditUser, 400);
        Assertions.assertJsonByName(responseEditUser, "error", "This user can only delete their own account.");

    }

}
