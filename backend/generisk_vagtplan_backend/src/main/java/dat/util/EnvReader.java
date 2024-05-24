package dat.util;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class EnvReader
{
    public static String getEnv()
    {
        try {
            return Files.readAllLines(
                            Paths.get("src/main/java/dat/util/Env.txt"))
                    .get(0);
        } catch (IOException e) {
            return "";
        }
    }
}
