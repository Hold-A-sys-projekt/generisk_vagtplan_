package dat.dao;

import dat.config.HibernateConfig;
import dat.model.ExampleEntity;
import jakarta.persistence.EntityManagerFactory;

public class ExampleDAO extends DAO<ExampleEntity> {

    private static ExampleDAO INSTANCE;

    private ExampleDAO(EntityManagerFactory emf) {
        super(ExampleEntity.class, emf);
    }

    public static ExampleDAO getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new ExampleDAO(HibernateConfig.getEntityManagerFactory());
        }

        return INSTANCE;
    }
}