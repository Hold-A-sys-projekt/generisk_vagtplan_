package dat.dao;

import dat.config.HibernateConfig;
import dat.model.Employee;
import dat.model.Manager;
import jakarta.persistence.EntityManager;

import jakarta.persistence.EntityManagerFactory;

public class ManagerDAO extends DAO<Manager>{
    private static ManagerDAO INSTANCE;

    private ManagerDAO(EntityManagerFactory emf) {
        super(Manager.class, emf);
    }

    public static ManagerDAO getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new ManagerDAO(HibernateConfig.getEntityManagerFactory());
        }

        return INSTANCE;
    }
    public Employee addEmployee(Employee employee) {
        try (EntityManager em = emf.createEntityManager()) {
            em.getTransaction().begin();
            em.persist(employee);
            em.getTransaction().commit();
            return employee;
        }
    }


}
