package dat.util;

import java.util.Random;

public class PasswordGenerator {

    private static char[] passwordGenerator(){
    int passwordLength = 10;

        String Capital_chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        String Small_chars = "abcdefghijklmnopqrstuvwxyz";
        String numbers = "0123456789";
        String symbols = "!@$%&?";

        String values = Capital_chars + Small_chars +
                numbers + symbols;

        Random rndm_method = new Random();

        char[] password = new char[passwordLength];

        for (int i = 0; i < passwordLength; i++)
        {
            password[i] =
                    values.charAt(rndm_method.nextInt(values.length()));

        }
        return password;
    }
}


