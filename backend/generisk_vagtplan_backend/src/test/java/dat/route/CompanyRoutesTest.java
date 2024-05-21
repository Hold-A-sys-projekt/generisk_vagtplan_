package dat.route;

import dat.config.ApplicationConfig;
import dat.config.HibernateConfig;
import dat.dao.UserDAO;
import dat.exception.AuthorizationException;
import dat.model.Company;
import dat.model.User;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import org.junit.jupiter.api.*;

import java.util.List;

import static io.restassured.RestAssured.given;

class CompanyRoutesTest {
    //Role company_admin needs to be added for the create method to work.

    private static EntityManagerFactory emf;
    private String BASE_URL = "http://localhost:7070/api/companies";
    private User user = null;
    static UserDAO userDAO;
    private EntityManager em = emf.createEntityManager();

    @BeforeAll
    static void init() {
        HibernateConfig.setTestStatus(false);
        emf = HibernateConfig.getEntityManagerFactory();
        userDAO = UserDAO.getInstance();
        ApplicationConfig.startServer(7070);
    }

    @BeforeEach
    void setUp() throws AuthorizationException {
        em.getTransaction().begin();
        List<User> users = em.createQuery("SELECT u FROM User u", User.class).getResultList();

        for (User user : users) {
            user.setCompany(null);
            em.merge(user);
        }
        em.getTransaction().commit();

        em.getTransaction().begin();
        em.createQuery("DELETE FROM Company c").executeUpdate();
        em.createQuery("DELETE FROM User u").executeUpdate();
        em.getTransaction().commit();

        userDAO.registerUser("testUser3", "testUser3", "testUser3", "USER");
        user = userDAO.getVerifiedUser("testUser3", "testUser3");
    }


    @AfterEach
    void tearDown() {
        em.getTransaction().begin();
        List<User> users = em.createQuery("SELECT u FROM User u", User.class).getResultList();

        for (User user : users) {
            user.setCompany(null);
            em.merge(user);
        }
        em.getTransaction().commit();

        em.getTransaction().begin();
        em.createQuery("DELETE FROM Company c").executeUpdate();
        em.createQuery("DELETE FROM User u").executeUpdate();
        em.getTransaction().commit();
    }

    @AfterAll
    static void afterAll() {
        emf.close();
        ApplicationConfig.stopServer();
    }

    @Test
    void create() {
        Company company1 = new Company("testCompany4", user);
        given()
                .contentType("application/json")
                .body(company1)
                .when()
                .post(BASE_URL + "/create")
                .then()
                .assertThat()
                .statusCode(201);
    }

}