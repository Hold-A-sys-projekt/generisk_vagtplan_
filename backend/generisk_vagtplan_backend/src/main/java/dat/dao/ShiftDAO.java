package dat.dao;

import dat.config.HibernateConfig;
import dat.model.Employee;
import dat.model.RouteRoles;
import dat.model.Shift;
import dat.model.User;
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


}
