package dat.dao;

import dat.config.HibernateConfig;
import dat.exception.ApiException;
import dat.exception.DatabaseException;
import dat.model.Shift;
import dat.model.User;

import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.PersistenceException;

import java.time.LocalDateTime;
import java.util.Optional;

public class EmployeeDAO extends DAO<User> {

    private static EmployeeDAO INSTANCE;

    private EmployeeDAO(EntityManagerFactory emf) {
        super(User.class, emf);
    }

    public static EmployeeDAO getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new EmployeeDAO(HibernateConfig.getEntityManagerFactory());
        }
        return INSTANCE;
    }

    public Optional<Shift> readCurrentShift(int userId, LocalDateTime currentDate) {
        // No need to catch NoResultException, as the result is Optional. Added try-catch to catch other possible DB errors and throw an ApiException.
        try {
            return emf.createEntityManager()
                    .createQuery("SELECT s FROM Shift s WHERE s.user.id = :userId AND s.shiftStart >= :currentDate ORDER BY s.shiftStart ASC", Shift.class)
                    .setParameter("userId", userId)
                    .setParameter("currentDate", currentDate)
                    .setMaxResults(1)
                    .getResultStream()
                    .findFirst();
        } catch (PersistenceException e) {
            throw new DatabaseException(500, "Something went wrong. Please try again or contact support!");
        }
    }
}
