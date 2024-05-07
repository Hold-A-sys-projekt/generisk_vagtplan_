package dat.dao;

import dat.config.HibernateConfig;
import dat.model.ExampleEntity;
import dat.model.Shift;
import dat.model.User;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;

import java.util.List;

public class ShiftDAO extends DAO<Shift> {
    private static ShiftDAO INSTANCE;

    private ShiftDAO(EntityManagerFactory emf) {
        super(Shift.class, emf);
    }

    public static ShiftDAO getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new ShiftDAO(HibernateConfig.getEntityManagerFactory());
        }

        return INSTANCE;
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
            User employee = em.find(User.class, employeeId);
            shift.setEmployee(employee);
            em.persist(shift);
            em.getTransaction().commit();
            return shift;
        } finally {
            em.close();
        }
    }

    public void delete (Shift shift){
        EntityManager em = emf.createEntityManager();
            try {
                em.getTransaction().begin();
                Shift shiftToDelete = em.find(Shift.class, shift.getId());
                em.remove(shiftToDelete);
                em.getTransaction().commit();
            } finally {
                em.close();
            }
        }

}
