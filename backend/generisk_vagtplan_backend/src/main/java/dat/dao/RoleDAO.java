package dat.dao;

import dat.config.HibernateConfig;
import dat.model.RouteRoles;
import jakarta.persistence.EntityManagerFactory;

public class RoleDAO extends DAO<RouteRoles> {
    private static RoleDAO INSTANCE = null;

    private RoleDAO(EntityManagerFactory emf) {
        super(RouteRoles.class, emf);
    }

    public static RoleDAO getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new RoleDAO(HibernateConfig.getEntityManagerFactory());
        }

        return INSTANCE;
    }
}
