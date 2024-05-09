package dat.dao;

import dat.config.HibernateConfig;
import dat.model.SwapShifts;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;

import java.util.List;

public class SwapShiftsDAO extends DAO<SwapShifts>{

    private static SwapShiftsDAO INSTANCE;

    private SwapShiftsDAO(EntityManagerFactory emf) {
        super(SwapShifts.class, emf);
    }

    public static SwapShiftsDAO getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new SwapShiftsDAO(HibernateConfig.getEntityManagerFactory());
        }

        return INSTANCE;
    }


    public List<SwapShifts> getSwapShifts() {
        try(EntityManager em = emf.createEntityManager()) {
            return SwapShiftsDAO.getInstance().readAll();
    }
}

    public void updateSwapAcceptance(int swapId, String isAccepted) {
        EntityManager em = null;
        try {
            em = emf.createEntityManager();
            em.getTransaction().begin();
            SwapShifts swap = em.find(SwapShifts.class, swapId);
            if (swap != null) {
                swap.setIsAccepted(isAccepted);
                em.merge(swap);
            }
            em.getTransaction().commit();
        } catch (Exception e) {
            if (em != null && em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            throw new RuntimeException("Failed to update swap acceptance", e);
        } finally {
            if (em != null) {
                em.close();
            }
        }

    }
}
