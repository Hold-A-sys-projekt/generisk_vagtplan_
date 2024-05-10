package dat.dao;

import dat.config.HibernateConfig;
import dat.model.*;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;

import java.util.List;

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

    public List<Shift> getShiftsByEmployeeId(int employeeId) {
        return emf.createEntityManager().createQuery("SELECT s FROM Shift s WHERE s.employee.id = :employeeId", Shift.class)
                .setParameter("employeeId", employeeId)
                .getResultList();
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


}
