package dat.dao;

import dat.config.HibernateConfig;
import dat.controller.ShiftController;
import dat.model.Role;
import dat.model.Shift;
import dat.model.User;
import dat.util.DBHouseKeeper;
import jakarta.persistence.EntityManagerFactory;
import org.junit.Test;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class SoftDeleteTest {

    // Truncate all tables in the database, and run PopulateConfig.main() before running this!

    EntityManagerFactory emf = HibernateConfig.getEntityManagerFactory();
    DAO<User> userDAO = new DAO<>(User.class, emf);
    DBHouseKeeper houseKeeper = new DBHouseKeeper();

    @Test
    public void createOldUser() {
        User user = new User("sovs@kage.dk", "svend-erik", "1234");
        user.setRole(new Role("employee"));
        user.setDeleted(true);
        user.setDeletedOn(LocalDateTime.now().minusMonths(7));

        userDAO.create(user);

        User userFromDB = userDAO.readById(user.getId()).get();
        assertEquals(user.getEmail(), userFromDB.getEmail());
        assertTrue(user.isDeleted());

        userDAO.delete(user);
    }

    @Test
    public void readAllNonDeleted() {
        List<User> users = userDAO.readAllNonDeleted();

        for (User user : users) {
            assertFalse(user.isDeleted());
        }
    }

    @Test
    public void vacuumTest() {
        houseKeeper.vacuum();

        List<User> users = userDAO.readAll();

        assertEquals(4, users.size());
    }
}