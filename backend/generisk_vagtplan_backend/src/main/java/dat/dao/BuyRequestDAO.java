package dat.dao;

import dat.config.HibernateConfig;
import dat.model.BuyRequest;
import jakarta.persistence.EntityManagerFactory;

public class BuyRequestDAO extends DAO<BuyRequest>
{
    private static BuyRequestDAO INSTANCE;


    private BuyRequestDAO(Class<BuyRequest> clazz, EntityManagerFactory emf)
    {
        super(clazz, emf);
    }

    public static BuyRequestDAO getInstance()
    {
        if (INSTANCE == null)
        {
            INSTANCE = new BuyRequestDAO(BuyRequest.class, HibernateConfig.getEntityManagerFactory());
        }
        return INSTANCE;
    }


}
