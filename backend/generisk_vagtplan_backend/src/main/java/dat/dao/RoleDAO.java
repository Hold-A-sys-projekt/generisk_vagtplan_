package dat.dao;

import dat.config.HibernateConfig;
import dat.model.Role;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;

public class RoleDAO extends DAO<Role> {
    private static RoleDAO INSTANCE = null;

    private RoleDAO(EntityManagerFactory emf) {
        super(Role.class, emf);
    }

    public static RoleDAO getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new RoleDAO(HibernateConfig.getEntityManagerFactory());
        }

        return INSTANCE;
    }

    public Role readByName(String roleName) {

        try(EntityManager em = emf.createEntityManager()) {
            return em.createQuery("SELECT r FROM Role r WHERE r.name = :name", Role.class)
                    .setParameter("name", roleName)
                    .getSingleResult();
        } catch (Exception e) {
            System.out.println("Role does not exist");
            return null;
        }
    }


}
