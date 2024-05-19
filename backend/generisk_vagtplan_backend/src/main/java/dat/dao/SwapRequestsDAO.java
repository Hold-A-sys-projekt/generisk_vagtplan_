package dat.dao;

import dat.config.HibernateConfig;
import dat.model.SwapRequests;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;

public class SwapRequestsDAO extends DAO<SwapRequests> {

    private static SwapRequestsDAO INSTANCE;

    private SwapRequestsDAO(EntityManagerFactory emf) {
        super(SwapRequests.class, emf);
    }

    public static SwapRequestsDAO getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new SwapRequestsDAO(HibernateConfig.getEntityManagerFactory());
        }
        return INSTANCE;
    }

    public void createRequest(SwapRequests request) {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(request);
            em.getTransaction().commit();
        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            throw new RuntimeException("Failed to create swap request: " + e.getMessage(), e);
        } finally {
            em.close();
        }
    }

    public void updateRequestAcceptance(int requestId, String isAccepted) {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            SwapRequests request = em.find(SwapRequests.class, requestId);
            if (request != null) {
                request.setIsAccepted(isAccepted);
                em.merge(request);
                em.getTransaction().commit();
            }
        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            throw new RuntimeException("Failed to update swap request acceptance: " + e.getMessage(), e);
        } finally {
            em.close();
        }
    }

    public SwapRequests findById(int id) {
        EntityManager em = emf.createEntityManager();
        try {
            return em.find(SwapRequests.class, id);
        } finally {
            em.close();
        }
    }
}
