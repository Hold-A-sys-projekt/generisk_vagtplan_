package dat;

import dat.config.ApplicationConfig;
import dat.model.util.PopulateDummyShifts;
import dat.model.util.PopulateDummyUsers;

public class Main {

    public static void main(String[] args) {
        PopulateDummyUsers.generateAndRegisterDummyUsers(10, "user");

        PopulateDummyShifts.persistDummyShifts();

        ApplicationConfig.startServer(7070);
    }
}