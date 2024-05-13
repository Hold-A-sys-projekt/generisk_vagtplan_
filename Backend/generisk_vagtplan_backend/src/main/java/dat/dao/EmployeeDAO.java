package dat.dao;

import dat.config.HibernateConfig;
import dat.exception.ApiException;
import dat.exception.DatabaseException;
import dat.model.Employee;
import dat.model.Shift;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.PersistenceException;

import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;

public class EmployeeDAO extends DAO<Employee> {


    private static EmployeeDAO INSTANCE;

    private EmployeeDAO(EntityManagerFactory emf) {
        super(Employee.class, emf);
    }

    public static EmployeeDAO getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new EmployeeDAO(HibernateConfig.getEntityManagerFactory());
        }
        return INSTANCE;
    }

    public Optional<Shift> readCurrentShift(int employeeId, LocalDateTime currentDate) {
        // No need to catch NoResultException, as the result is Optional. Added try-catch catch other possible DB errors and throw an ApiException.
        try {
            return emf.createEntityManager()
                    .createQuery("SELECT s FROM Shift s WHERE s.employee.id = :employeeId AND s.shiftStart >= :currentDate ORDER BY s.shiftStart ASC", Shift.class)
                    .setParameter("employeeId", employeeId)
                    .setParameter("currentDate", currentDate)
                    .setMaxResults(1)
                    .getResultStream()
                    .findFirst();
        } catch (PersistenceException e) {
            throw new DatabaseException(500, "Something went wrong. Please try again or contact support!");
        }

    }


}
