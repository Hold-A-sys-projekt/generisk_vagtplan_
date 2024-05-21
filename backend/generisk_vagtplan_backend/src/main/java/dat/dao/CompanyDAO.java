package dat.dao;

import dat.config.HibernateConfig;
import dat.model.Company;
import dat.model.Role;
import dat.model.User;
import jakarta.persistence.EntityManager;
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
        try (EntityManager em = this.emf.createEntityManager()) {
            em.getTransaction().begin();
            em.persist(entity);
            User user = entity.getCompanyAdmin();
            user.setCompany(entity);
            user.setRole(new Role("company_admin"));
            em.merge(user);
            em.getTransaction().commit();
            return entity;
        }
    }
}
