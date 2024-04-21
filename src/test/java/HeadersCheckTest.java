import io.restassured.RestAssured;
import io.restassured.http.Headers;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class HeadersCheckTest {

    @Test
    public void testHeadersCheck() {

        Response response = RestAssured
                .given()
                .get("https://playground.learnqa.ru/api/homework_header")
                .andReturn();

        Headers headers = response.getHeaders();

        Map<String, String> expectedResalt = new HashMap<>();
        expectedResalt.put("Content-Length", "15");
        expectedResalt.put("Content-Type", "application/json");
        expectedResalt.put("Server", "Apache");
        expectedResalt.put("Connection", "keep-alive");
        expectedResalt.put("Keep-Alive", "timeout=10");
        expectedResalt.put("Cache-Control", "max-age=0");
        expectedResalt.put("x-secret-homework-header", "Some secret value");


        for (String expectedHeaderKey : expectedResalt.keySet()) {
            String expectedHeaderValue = expectedResalt.get(expectedHeaderKey);

            assertTrue(headers.hasHeaderWithName(expectedHeaderKey), "Response does'n have header with name " + expectedHeaderKey);
            assertEquals(expectedHeaderValue, headers.getValues(expectedHeaderKey).get(0), "Response does'n have " + expectedHeaderValue + " value for " + expectedHeaderKey + " header");
        }

    }
}
