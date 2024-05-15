package dat.util;

import java.util.Random;
import java.util.stream.Stream;

public class PasswordGenerator {

    public static String passwordGenerator(){
    int passwordLength = 10;

        String Capital_chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        String Small_chars = "abcdefghijklmnopqrstuvwxyz";
        String numbers = "0123456789";
        String symbols = "!@$%&?";

        String values = Capital_chars + Small_chars +
                numbers + symbols;

        Random rndm_method = new Random();

        StringBuilder password = new StringBuilder();

        for (int i = 0; i < passwordLength; i++)
        {
            password.append(values.charAt(rndm_method.nextInt(values.length())));

        }
        return password.toString();
    }
    
}


