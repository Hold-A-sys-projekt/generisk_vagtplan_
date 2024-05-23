package dat.controller;

import dat.config.ApplicationConfig;
import dat.config.HibernateConfig;
import dat.dao.CompanyDAO;
import dat.dao.DepartmentDAO;
import dat.dao.UserDAO;
import dat.exception.AuthorizationException;
import dat.model.Company;
import dat.model.Department;
import dat.model.Role;
import dat.model.User;
import jakarta.persistence.EntityManagerFactory;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.*;

class UserControllerTest {

    private static EntityManagerFactory emfTest;

    private static final int BASE_PORT = 7070;
    private static final String BASE_URL = "http://localhost:" + BASE_PORT + "/api/users";

    private static User user;
    private static User admin;

    private static Company company;
    private static Department department1;
    private static Department department2;

    private static UserDAO userDAO;
    private static CompanyDAO companyDAO;
    private static DepartmentDAO departmentDAO;

    private static UserController userController;

    @BeforeAll
    static void beforeAll() {
        HibernateConfig.setTestStatus(true);
        ApplicationConfig.startServer(BASE_PORT);
        emfTest = HibernateConfig.getEntityManagerFactory();
        userDAO = UserDAO.getInstance();
        companyDAO = CompanyDAO.getInstance();
        departmentDAO = DepartmentDAO.getInstance();
        try {
            user = userDAO.registerUser("test@mail.dk", "test", "test", "user");
            admin = userDAO.registerUser("admin@admin.dk", "admin", "admin", "admin");

            company = companyDAO.create(new Company("testCompany", admin));
            department1 = departmentDAO.create(new Department("testDepartment1", company));
            department2 = departmentDAO.create(new Department("testDepartment2", company));

            user.setDepartment(department1);
            userDAO.update(user);
        } catch (AuthorizationException e) {
            throw new RuntimeException(e);
        }
        userController = new UserController(userDAO);
    }

    @BeforeEach
    void beforeEach() {
        user.setDepartment(department1);
        user.setRole(Role.of("user"));
        user.setUsername("test");
        user.setEmail("test@mail.dk");
        userDAO.update(user);
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

    @Test
    void updateDepartment() {
        // Test that the department is updated
        String userId = String.valueOf(user.getId());
        String departmentName = department2.getName();
        String userDepartment = user.getDepartment().getName();

        given()
                .contentType("application/json")
                .body("{\"department\": { \"name\": \"" + departmentName + "\", \"id\": " + department2.getId() + " }}")
                .when()
                .put(BASE_URL + "/" + userId + "/department")
                .then()
                .statusCode(200);

        User updatedUser = userDAO.readById(user.getId()).orElse(null);
        assertEquals(departmentName, updatedUser.getDepartment().getName());
    }

    @Test
    void updateRole() {
        // Test that the role is updated
        String userId = String.valueOf(user.getId());
        String roleName = "admin";
        String userRole = user.getRole().getName();

        given()
                .contentType("application/json")
                .body("{\"role\": { \"name\": \"" + roleName + "\" }}")
                .when()
                .put(BASE_URL + "/" + userId + "/role")
                .then()
                .statusCode(200);

        User updatedUser = userDAO.readById(user.getId()).orElse(null);
        assertEquals(roleName, updatedUser.getRole().getName());
    }

    @Test
    void updateUsernameAndEmail() {
        // Test that the username and email is updated
        String userId = String.valueOf(user.getId());
        String newUsername = "newUsername";
        String newEmail = "newEmail@email.dk";

        given()
                .contentType("application/json")
                .body("{\"username\": \"" + newUsername + "\", \"email\": \"" + newEmail + "\"}")
                .when()
                .put(BASE_URL + "/" + userId + "/email-username")
                .then()
                .statusCode(200);

        User updatedUser = userDAO.readById(user.getId()).orElse(null);

        // Check if both fields in database has been updated
        assertEquals(newUsername, updatedUser.getUsername());
        assertEquals(newEmail, updatedUser.getEmail());
    }
}