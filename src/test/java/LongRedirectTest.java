import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;

public class LongRedirectTest {

    @Test
    public void testLongRedirect() {

        int statusCode;
        int counter = 0;
        String url = "https://playground.learnqa.ru/api/long_redirect";

        while (true) {
            Response response = RestAssured
                    .given()
                    .redirects()
                    .follow(false)
                    .when()
                    .get(url)
                    .andReturn();

            statusCode = response.getStatusCode();

            if (statusCode != 200) {
                url = response.getHeader("Location");
                counter++;
            } else break;
        }
        System.out.println(url);
        System.out.println(counter);
    }

}
