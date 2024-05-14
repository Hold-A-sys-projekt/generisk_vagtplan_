package dat.dao;

import dat.config.HibernateConfig;
import dat.model.Role;
import dat.model.User;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;

public class ManagerDAO extends DAO<User> {
    private static ManagerDAO INSTANCE;

    private ManagerDAO(EntityManagerFactory emf) {
        super(User.class, emf);
    }

    public static ManagerDAO getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new ManagerDAO(HibernateConfig.getEntityManagerFactory());
        }

        return INSTANCE;
    }

    public User addEmployee(User user) {

        if (!user.getRole().equals("EMPLOYEE")) {
            throw new IllegalArgumentException("You may only add employees to the system");
        }

        try (EntityManager em = emf.createEntityManager()) {
            em.getTransaction().begin();
            em.persist(user);
            em.getTransaction().commit();
            return user;
        }
    }

    public User getEmployee(int id) {
        try (EntityManager em = emf.createEntityManager()) {
            return em.find(User.class, id);
        }
    }

    public User updateEmployeeRole(User user, String newRole) {
        try (EntityManager em = emf.createEntityManager()) {
            em.getTransaction().begin();

            User updatedUser = em.find(User.class, user.getId());
            if (updatedUser != null) {
                updatedUser.setRole(new Role(newRole));
                em.merge(updatedUser);
            }
            em.getTransaction().commit();
            return updatedUser;
        }
    }
}
