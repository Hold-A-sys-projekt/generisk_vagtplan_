package dat.dao;

import dat.config.HibernateConfig;
import dat.model.*;
import dat.exception.DatabaseException;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.NoResultException;
import jakarta.persistence.PersistenceException;

import java.util.List;
import java.util.Set;

public class ShiftDAO extends DAO<Shift>{


    private static ShiftDAO INSTANCE;

    public static ShiftDAO getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new ShiftDAO(HibernateConfig.getEntityManagerFactory());
        }
        return INSTANCE;
    }
    private ShiftDAO(EntityManagerFactory emf) {
        super(Shift.class, emf);
    }

    public List<Shift> getShiftsByEmployeeId(int employeeId) throws DatabaseException {
        try {
            return emf.createEntityManager().createQuery("SELECT s FROM Shift s WHERE s.employee.id = :employeeId", Shift.class)
                    .setParameter("employeeId", employeeId)
                    .getResultList();
            // Catches if no shifts are found
        } catch (PersistenceException e) {
            // Throws exception which is caught by the apiException method in the ExceptionManagerHandler
            throw new DatabaseException(404, "No shifts found for employee with id: " + employeeId);
        }
    }

    public Shift create (Shift shift, int employeeId) {
        EntityManager em = emf.createEntityManager();

        try {
            em.getTransaction().begin();
            Employee employee = em.find(Employee.class, employeeId);
            shift.setEmployee(employee);
            em.persist(shift);
            em.getTransaction().commit();
            return shift;
        } finally {
            em.close();
        }


    }
    //get shift status
    public Shift getShiftStatus(int shiftId) {
        return emf.createEntityManager().createQuery("SELECT s FROM Shift s WHERE s.id = :shiftId", Shift.class)
                .setParameter("shiftId", shiftId)
                .getSingleResult();
    }


    public Shift updateShiftStatus(int shiftId, Status status){
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            Shift shift = em.find(Shift.class, shiftId);
            if (shift == null) {
                throw new IllegalStateException("Shift with ID " + shiftId + " not found.");
            }
            shift.setStatus(status);
            em.getTransaction().commit();
            return shift;
        } finally {
            em.close();
        }
    }


    public List<Shift> getByStatus(Status status)
    {
        try (EntityManager em = emf.createEntityManager()) {
            return em.createQuery("SELECT s FROM Shift s WHERE s.status = :status", Shift.class)
                    .setParameter("status", status)
                    .getResultList();
        }
    }
}
