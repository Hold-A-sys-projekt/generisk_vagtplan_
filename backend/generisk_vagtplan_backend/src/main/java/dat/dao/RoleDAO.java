package dat.dao;

import dat.config.HibernateConfig;
import dat.model.Role;
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
}
