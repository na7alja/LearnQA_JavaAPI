package lib;

import org.apache.commons.lang3.RandomStringUtils;

import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;

public class DataGenerator {
    public static String getRandomEmail() {
        String timestamp = new SimpleDateFormat("yyyyMMddHHmmss").format(new java.util.Date());
        return "learnqa" + timestamp + "@exampl.com";
    }

    public  static String getRandomName(){
        int length = 251;
        boolean useLetters = true;
        boolean useNumbers = false;
        String generatedString = RandomStringUtils.random(length, useLetters, useNumbers);
        return generatedString;
    }

    public static Map<String, String> getRegistrationData() {
        Map<String, String> date = new HashMap<>();
        date.put("email", DataGenerator.getRandomEmail());
        date.put("password", "123");
        date.put("username", "learnqa");
        date.put("firstName", "learnqa");
        date.put("lastName", "learnqa");
        return date;
    }

    public static Map<String, String> getRegistrationData(Map<String, String> nonDefaultValues) {
        Map<String, String> defaultValues = DataGenerator.getRegistrationData();

        Map<String, String> userDate = new HashMap<>();
        String[] keys = {"email", "password", "username", "firstName", "lastName"};
        for (String key : keys) {
            if (nonDefaultValues.containsKey(key)) {
                userDate.put(key, nonDefaultValues.get(key));
            } else {
                userDate.put(key, defaultValues.get(key));
            }
        }
        return userDate;
    }

}
