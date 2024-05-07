package dat.dao;

import dat.config.HibernateConfig;
import dat.model.Employee;
import dat.model.RouteRoles;
import jakarta.persistence.EntityManagerFactory;

public class EmployeeDAO extends DAO<Employee>{
    private static EmployeeDAO INSTANCE;

    private final DAO<RouteRoles> ROLE_DAO = new DAO<>(RouteRoles.class, emf);

    private EmployeeDAO(EntityManagerFactory emf) {
        super(Employee.class, emf);
    }

    public static EmployeeDAO getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new EmployeeDAO(HibernateConfig.getEntityManagerFactory());
        }

        return INSTANCE;
    }

}
