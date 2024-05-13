import dat.util.EmailSender;

import java.util.List;

public class EmailTest {
    public static void main(String[] args) {
        EmailSender.sendEmail("mail@gmail.com",
                "Test !",
                List.of("Hallo, ", "World!"),
                true);
    }
}
