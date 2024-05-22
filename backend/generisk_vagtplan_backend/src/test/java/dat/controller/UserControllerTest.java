package dat.controller;

import dat.config.ApplicationConfig;
import dat.config.HibernateConfig;
import dat.dao.UserDAO;
import dat.exception.AuthorizationException;
import dat.model.User;
import jakarta.persistence.EntityManagerFactory;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.*;

class UserControllerTest {

    private static EntityManagerFactory emfTest;

    private static final int BASE_PORT = 7070;
    private static final String BASE_URL = "http://localhost:" + BASE_PORT + "/api/users";

    private static User user;

    private static UserDAO userDAO;

    private static UserController userController;

    @BeforeAll
    static void beforeAll() {
        HibernateConfig.setTestStatus(true);
        ApplicationConfig.startServer(BASE_PORT);
        emfTest = HibernateConfig.getEntityManagerFactory();
        userDAO = UserDAO.getInstance();
        try {
            user = userDAO.registerUser("test@mail.dk", "test", "test", "user");
        } catch (AuthorizationException e) {
            throw new RuntimeException(e);
        }
        userController = new UserController(userDAO);
    }

    @AfterAll
    static void tearDown() {
        HibernateConfig.setTestStatus(false);
        ApplicationConfig.stopServer();
    }

    @Test
    void resetPassword() {
        // Test that the password is reset
        String oldPassword = user.getPassword();
        String userId = String.valueOf(user.getId());

        given()
                .when()
                .get(BASE_URL + "/" + userId + "/reset-password")
                .then()
                .statusCode(200);

        User updatedUser = userDAO.readById(user.getId()).orElse(null);
        assertNotEquals(oldPassword, updatedUser.getPassword());
    }
}