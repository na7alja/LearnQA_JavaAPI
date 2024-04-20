import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class LengthCheckTest {

    @Test
    public void testLengthCheck() {
        String someString = "0123456789012345";
        assertTrue(someString.length() > 15, "String length must be greater than 15");

    }
}
