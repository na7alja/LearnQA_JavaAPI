import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import org.junit.jupiter.api.Test;

public class TokenTest {
    @Test
    public void testToken() throws InterruptedException {

        JsonPath responseWithoutToken = RestAssured
                .get("https://playground.learnqa.ru/ajax/api/longtime_job")
                .jsonPath();

        String token = responseWithoutToken.get("token");
        int seconds = responseWithoutToken.getInt("seconds");

        Thread.sleep(1000 * seconds);

        JsonPath responseWithToken = RestAssured
                .given()
                .queryParam("token", token)
                .when()
                .get("https://playground.learnqa.ru/ajax/api/longtime_job")
                .jsonPath();

        String result = responseWithToken.get("result");
        String status = responseWithToken.get("status");

        System.out.println("result " + result);
        System.out.println("status " + status);

    }
}
