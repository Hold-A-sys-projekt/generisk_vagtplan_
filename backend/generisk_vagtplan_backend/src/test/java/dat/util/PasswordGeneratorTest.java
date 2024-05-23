package dat.util;

import org.junit.jupiter.api.Test;

import java.util.regex.Pattern;

import static org.junit.jupiter.api.Assertions.*;

class PasswordGeneratorTest {
    @Test
    void passwordGenerator() {

        // First test if a correct? password is created
        String password = PasswordGenerator.passwordGenerator();
        assertNotNull(password);
        assertEquals(10, password.length());

        // Test a second password
        String newPassword = PasswordGenerator.passwordGenerator();
        assertNotNull(newPassword);
        assertEquals(10, newPassword.length());

        // Test that the password is not the same every time.
        assertNotEquals(password, newPassword);
    }

    @Test
    void containsSymbols() {
        Pattern symbolPattern = Pattern.compile("^(?=.*?[#?!@$ %^&*-]).{10,}$");
        for (int i = 0; i < 10; i++) {
            // Test that a generated password contains at least one symbol
            String pw = PasswordGenerator.passwordGenerator();
            assertTrue(symbolPattern.matcher(pw).matches());
        }
    }

    @Test
    void containsDigits() {
        Pattern digitPattern = Pattern.compile("^(?=.*?[0-9]).{10,}$");

        for (int i = 0; i < 10; i++) {
            // Test that a generated password contains at least one digit
            String pw = PasswordGenerator.passwordGenerator();
            assertTrue(digitPattern.matcher(pw).matches());
        }
    }

    @Test
    void containsCapitalCharacters() {
        Pattern capitalPattern = Pattern.compile("^(?=.*?[A-Z]).{10,}$");

        for (int i = 0; i < 10; i++) {
            // Test that a generated password contains at least one capital character
            String pw = PasswordGenerator.passwordGenerator();
            assertTrue(capitalPattern.matcher(pw).matches());
        }
    }

    @Test
    void containsSmallCharacters() {
        Pattern smallPattern = Pattern.compile("^(?=.*?[a-z]).{10,}$");

        for (int i = 0; i < 10; i++) {
            // Test that a generated password contains at least one small character
            String pw = PasswordGenerator.passwordGenerator();
            assertTrue(smallPattern.matcher(pw).matches());
        }
    }
}