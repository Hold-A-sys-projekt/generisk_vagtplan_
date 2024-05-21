package dat.util;

import java.util.*;

public class PasswordGenerator {

    public static String passwordGenerator() {
        int passwordLength = 10;

        String capitalChars = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        String smallChars = "abcdefghijklmnopqrstuvwxyz";
        String numbers = "0123456789";
        String symbols = "!@$%&?";

        Random random = new Random();

        List<Character> passwordChars = new ArrayList<>();

        int capitalCharCount = random.nextInt(3) + 1;
        for (int i = 0; i < capitalCharCount; i++) {
            passwordChars.add(capitalChars.charAt(random.nextInt(capitalChars.length())));
        }

        int numberCount = random.nextInt(3) + 1;
        for (int i = 0; i < numberCount; i++) {
            passwordChars.add(numbers.charAt(random.nextInt(numbers.length())));
        }

        int symbolCount = random.nextInt(2) + 1;
        for (int i = 0; i < symbolCount; i++) {
            passwordChars.add(symbols.charAt(random.nextInt(symbols.length())));
        }

        int smallCharCount = passwordLength - capitalCharCount - numberCount - symbolCount;
        for (int i = 0; i < smallCharCount; i++) {
            passwordChars.add(smallChars.charAt(random.nextInt(smallChars.length())));
        }

        Collections.shuffle(passwordChars, random);
        StringBuilder password = new StringBuilder();

        for (char c : passwordChars) {
            password.append(c);
        }
        return password.toString();
    }
    
}


