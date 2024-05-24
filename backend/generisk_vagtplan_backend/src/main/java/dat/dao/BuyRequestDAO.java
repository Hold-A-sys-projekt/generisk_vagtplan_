package dat.dao;

import dat.config.HibernateConfig;
import dat.model.BuyRequest;
import jakarta.persistence.EntityManager;
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

    public boolean exists(BuyRequest buyRequest)
    {
        try (EntityManager em = emf.createEntityManager())
        {
            return !em.createQuery("SELECT b FROM BuyRequest b WHERE b.user.id = :userId AND b.shift.id = :shiftId", BuyRequest.class)
                    .setParameter("userId", buyRequest.getUser().getId())
                    .setParameter("shiftId", buyRequest.getShift().getId())
                    .getResultList().isEmpty();
        }
    }
}
