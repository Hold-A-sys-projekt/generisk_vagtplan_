package dat.dao;

import dat.config.HibernateConfig;
import dat.dto.SwapShiftsUserDTO;
import dat.model.SwapRequests;
import dat.model.SwapShifts;
import dat.model.User;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;

import java.util.List;

public class SwapShiftsDAO extends DAO<SwapShifts> {

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

    public void createSwap(SwapShifts swap) {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(swap);
            em.getTransaction().commit();
        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            throw new RuntimeException("Failed to create swap: " + e.getMessage(), e);
        } finally {
            em.close();
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
        SwapRequests request = swap.getRequest();
        User user1 = request.getShift1().getUser();
        User user2 = request.getShift2().getUser();

        // Perform the swap
        request.getShift1().setUser(user2);
        request.getShift2().setUser(user1);

        // Persist the changes
        em.merge(request.getShift1());
        em.merge(request.getShift2());
    }

    public List<SwapShiftsUserDTO> getSwapShiftsWithUsernames() {
        try (EntityManager em = emf.createEntityManager()) {
            return em.createQuery(
                    "SELECT new dat.dto.SwapShiftsUserDTO(s.id, s.request.shift1.id, s.request.shift1.shiftStart, s.request.shift1.shiftEnd, s.request.shift1.user.username, s.request.shift2.id, s.request.shift2.shiftStart, s.request.shift2.shiftEnd, s.request.shift2.user.username, s.isAccepted) " +
                            "FROM SwapShifts s", SwapShiftsUserDTO.class
            ).getResultList();
        }
    }
}

