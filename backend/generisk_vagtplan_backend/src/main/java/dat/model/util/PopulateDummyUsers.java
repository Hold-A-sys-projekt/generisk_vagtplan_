package dat.model.util;

import dat.model.User;
import com.github.javafaker.Faker;
import dat.dao.UserDAO;
import dat.exception.AuthorizationException;

import java.util.ArrayList;
import java.util.List;

public class PopulateDummyUsers {
    private static final Faker faker = new Faker();

    public static void generateAndRegisterDummyUsers(int numberOfUsers, String defaultUserRole) {
        UserDAO userDAO = UserDAO.getInstance();
        List<User> registeredUsers = new ArrayList<>();

        for (int i = 0; i < numberOfUsers; i++) {
            String email = faker.internet().emailAddress();
            String username = faker.name().username();
            String password = faker.internet().password(8, 16, true, true, true);

            try {
                User registeredUser = userDAO.registerUser(email, username, password, defaultUserRole);
                registeredUsers.add(registeredUser);
                System.out.println("Successfully registered user: " + username);
            } catch (AuthorizationException e) {
                System.err.println("Failed to register user: " + username + " - " + e.getMessage());
            } catch (Exception e) {
                System.err.println("Unexpected error while registering user: " + username + " - " + e.getMessage());
            }
        }

        System.out.println("Total successfully registered users: " + registeredUsers.size());
    }
}