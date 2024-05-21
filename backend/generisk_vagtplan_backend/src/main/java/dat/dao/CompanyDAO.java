package dat.dao;

import dat.config.HibernateConfig;
import dat.model.Company;
import jakarta.persistence.EntityManagerFactory;

public class CompanyDAO extends DAO<Company> {
    private static CompanyDAO INSTANCE;

    private CompanyDAO(EntityManagerFactory emf) {
        super(Company.class, emf);
    }
    public static CompanyDAO getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new CompanyDAO(HibernateConfig.getEntityManagerFactory());
        }

        return INSTANCE;
    }

    @Override
    public Company create(Company entity) {
        return super.create(entity);
    }
}
