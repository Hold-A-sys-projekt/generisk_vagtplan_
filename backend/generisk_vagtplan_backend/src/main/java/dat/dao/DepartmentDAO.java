package dat.dao;

import dat.config.HibernateConfig;
import dat.model.Department;
import jakarta.persistence.EntityManagerFactory;

public class DepartmentDAO extends DAO<Department> {
    private static DepartmentDAO INSTANCE;

    private DepartmentDAO(EntityManagerFactory emf) {
        super(Department.class, emf);
    }

    public static DepartmentDAO getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new DepartmentDAO(HibernateConfig.getEntityManagerFactory());
        }

        return INSTANCE;
    }
}
