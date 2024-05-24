package dat.dao;

import dat.config.HibernateConfig;
import dat.exception.DatabaseException;
import dat.model.*;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.PersistenceException;

import java.util.List;

public class ShiftDAO extends DAO<Shift> {

    private static ShiftDAO INSTANCE;

    public static ShiftDAO getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new ShiftDAO(HibernateConfig.getEntityManagerFactory());
        }
        return INSTANCE;
    }

    private ShiftDAO(EntityManagerFactory emf) {
        super(Shift.class, emf);
    }

    public List<Shift> getShiftsByUserId(int userId) throws DatabaseException {
        try {
            // added ORDER BY id so the list of shifts is in order and stays the same even after punching in or out
            return emf.createEntityManager().createQuery("SELECT s FROM Shift s WHERE s.user.id = :userId ORDER BY id", Shift.class)
                    .setParameter("userId", userId)
                    .getResultList();
        } catch (PersistenceException e) {
            throw new DatabaseException(404, "No shifts found for user with id: " + userId);
        }
    }

    public Shift create(Shift shift, int userId) {
        try (EntityManager em = emf.createEntityManager();) {
            em.getTransaction().begin();
            User user = em.find(User.class, userId);
            shift.setUser(user);
            em.persist(shift);
            em.getTransaction().commit();
            return shift;
        } catch (Exception e) {
            throw e;
        }
    }

    //get shift status
    public Shift getShiftStatus(int shiftId) {
        return emf.createEntityManager().createQuery("SELECT s FROM Shift s WHERE s.id = :shiftId", Shift.class)
                .setParameter("shiftId", shiftId)
                .getSingleResult();
    }

    public Shift updateShiftStatus(int shiftId, Status status) {
        try (EntityManager em = emf.createEntityManager()) {
            em.getTransaction().begin();
            Shift shift = em.find(Shift.class, shiftId);
            if (shift == null) {
                throw new IllegalStateException("Shift with ID " + shiftId + " not found.");
            }
            shift.setStatus(status);
            em.getTransaction().commit();
            return shift;
        }
    }

    public List<Shift> getShiftsByIds(List<Integer> shiftIds) {
        return emf.createEntityManager().createQuery("SELECT s FROM Shift s WHERE s.id IN :shiftIds", Shift.class)
                .setParameter("shiftIds", shiftIds)
                .getResultList();
    }

    public List<Shift> getByStatus(Status status) {
        try (EntityManager em = emf.createEntityManager()) {
            return em.createQuery("SELECT s FROM Shift s WHERE s.status = :status", Shift.class)
                    .setParameter("status", status)
                    .getResultList();
        }
    }

    public List<BuyRequest> getBuyRequestsByShiftId(int shiftId) {
        try (EntityManager em = emf.createEntityManager()) {
            return em.createQuery("SELECT b FROM BuyRequest b WHERE b.shift.id = :shiftId", BuyRequest.class)
                    .setParameter("shiftId", shiftId)
                    .getResultList();
        }
    }

    public Shift updateShift(Shift updatedShift) throws DatabaseException {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            Shift existingShift = em.find(Shift.class, updatedShift.getId());

            if (existingShift == null) {
                throw new DatabaseException(404, "Shift with ID " + updatedShift.getId() + " not found.");
            }

            // Update the shift details
            existingShift.setShiftStart(updatedShift.getShiftStart());
            existingShift.setShiftEnd(updatedShift.getShiftEnd());
            existingShift.setStatus(updatedShift.getStatus());
            // You can add more fields to update as per your requirements

            em.getTransaction().commit();

            return existingShift;
        } catch (PersistenceException e) {
            em.getTransaction().rollback();
            throw new DatabaseException(500, "Failed to update shift with ID: " + updatedShift.getId());
        } finally {
            em.close();
        }
    }
}