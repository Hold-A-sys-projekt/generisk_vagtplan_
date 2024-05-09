import dat.util.EmailSender;

import java.util.List;

public class EmailTest {
    public static void main(String[] args) {
        EmailSender.sendEmail("oskarolsen7@gmail.com",
                "Test!",
                List.of("Hallo, ", "World!"),
                true);
    }
}
