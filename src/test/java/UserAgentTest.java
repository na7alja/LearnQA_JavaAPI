import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import model.UserAgentResponse;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class UserAgentTest {
    @ParameterizedTest
    @MethodSource("argsProviderFactory")
    public void testUserAgent(String userAgent, UserAgentResponse userAgentResponse) {

        JsonPath response = RestAssured
                .given()
                .header("User-Agent",userAgent)
                .get("https://playground.learnqa.ru/ajax/api/user_agent_check")
                .jsonPath();

        assertEquals(userAgentResponse.getPlatform(), response.get("platform"), "Incorrect value of field platform for User-Agent " + userAgent);
        assertEquals(userAgentResponse.getBrowser(), response.get("browser"), "Incorrect value of field browser for User-Agent " + userAgent);
        assertEquals(userAgentResponse.getDevice(), response.get("device"), "Incorrect value of field device for User-Agent " + userAgent);

    }

    static Stream<Arguments> argsProviderFactory() {
        return Stream.of(
                Arguments.of("Mozilla/5.0 (Linux; U; Android 4.0.2; en-us; Galaxy Nexus Build/ICL53F) AppleWebKit/534.30 (KHTML, like Gecko) Version/4.0 Mobile Safari/534.30",
                        new UserAgentResponse("Mobile", "No", "Android")),
                Arguments.of("Mozilla/5.0 (iPad; CPU OS 13_2 like Mac OS X) AppleWebKit/605.1.15 (KHTML, like Gecko) CriOS/91.0.4472.77 Mobile/15E148 Safari/604.1",
                        new UserAgentResponse("Mobile", "Chrome", "iOS")),
                Arguments.of("Mozilla/5.0 (compatible; Googlebot/2.1; +http://www.google.com/bot.html)",
                        new UserAgentResponse("Googlebot", "Unknown", "Unknown")),
                Arguments.of("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/91.0.4472.77 Safari/537.36 Edg/91.0.100.0",
                        new UserAgentResponse("Web", "Chrome", "No")),
                Arguments.of("Mozilla/5.0 (iPad; CPU iPhone OS 13_2_3 like Mac OS X) AppleWebKit/605.1.15 (KHTML, like Gecko) Version/13.0.3 Mobile/15E148 Safari/604.1",
                        new UserAgentResponse("Mobile", "No", "iPhone")
                )
        );
    }
}
