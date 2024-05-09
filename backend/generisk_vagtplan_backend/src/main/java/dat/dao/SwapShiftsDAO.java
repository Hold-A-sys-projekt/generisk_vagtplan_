package dat.dao;

import dat.config.HibernateConfig;
import dat.model.SwapShifts;
import dat.model.User;
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
        try (EntityManager em = emf.createEntityManager()) {
            return em.createQuery("SELECT s FROM SwapShifts s", SwapShifts.class).getResultList();
        }
    }

    public void updateSwapAcceptance(int swapId, String isAccepted) {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            SwapShifts swap = em.find(SwapShifts.class, swapId);
            if (swap != null) {
                swap.setIsAccepted(isAccepted);
                em.merge(swap);
                if ("Approved".equals(isAccepted)) {
                    performSwap(swap, em);
                }
                em.getTransaction().commit();
            }
        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            throw new RuntimeException("Failed to update swap acceptance: " + e.getMessage(), e);
        } finally {
            em.close();
        }
    }

    private void performSwap(SwapShifts swap, EntityManager em) {
        User user1 = swap.getShift1().getUser();
        User user2 = swap.getShift2().getUser();

        // Swapping the users associated with each shift
        swap.getShift1().setUser(user2);
        swap.getShift2().setUser(user1);

        em.merge(swap.getShift1());
        em.merge(swap.getShift2());
    }
}
