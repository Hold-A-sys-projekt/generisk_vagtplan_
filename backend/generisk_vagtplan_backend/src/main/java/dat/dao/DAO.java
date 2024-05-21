package dat.dao;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import lombok.Getter;

import java.util.List;
import java.util.Optional;

/**
 * This class is a DAO that stores entities in a database
 *
 * @param <EntityType> The type of the entity
 */
public class DAO<EntityType> {

    @Getter
    private final Class<EntityType> clazz;
    protected final EntityManagerFactory emf;

    public DAO(Class<EntityType> clazz, EntityManagerFactory emf) {
        this.clazz = clazz;
        this.emf = emf;
    }

    /**
     * Read an entity by its primary key
     *
     * @param id The primary key
     * @return An optional containing the entity if it exists
     */
    public Optional<EntityType> readById(Object id) {
        try (EntityManager em = this.emf.createEntityManager()) {
            return Optional.ofNullable(em.find(this.clazz, id));
        }
    }

    /**
     * Read all entities
     *
     * @return A list of all entities
     */
    public List<EntityType> readAll() {
        try (EntityManager em = this.emf.createEntityManager()) {
            return em.createQuery("SELECT t FROM " + this.clazz.getSimpleName() + " t", this.clazz).getResultList();
        }
    }

    /**
     * Create an entity
     *
     * @param entity The entity to create
     * @return The created entity
     */
    public EntityType create(EntityType entity) {
        try (EntityManager em = this.emf.createEntityManager()) {
            em.getTransaction().begin();
            em.persist(entity);
            em.getTransaction().commit();
            return entity;
        }
    }

    /**
     * Update an entity
     *
     * @param entity The entity to update
     * @return The updated entity
     */
    public EntityType update(EntityType entity) {
        try (EntityManager em = this.emf.createEntityManager()) {
            em.getTransaction().begin();
            EntityType mergedEntity = em.merge(entity);
            em.getTransaction().commit();
            return mergedEntity;
        }
    }

    /**
     * Delete an entity
     *
     * @param entity The entity to delete
     */
    public void delete(EntityType entity) {
        try (EntityManager em = this.emf.createEntityManager()) {
            em.getTransaction().begin();
            em.remove(em.merge(entity)); // Merge to ensure the entity is in the managed state
            em.getTransaction().commit();
        }
    }

    /**
     * Delete the saved entities
     */
    public void truncate() {
        final String tableName = this.clazz.getSimpleName();
        try (EntityManager em = this.emf.createEntityManager()) {
            em.getTransaction().begin();
            em.createNativeQuery("TRUNCATE TABLE " + tableName + " RESTART IDENTITY CASCADE").executeUpdate();
            em.getTransaction().commit();

            // Restart sequence if it exists
            try {
                em.getTransaction().begin();
                em.createNativeQuery("ALTER SEQUENCE " + tableName + "_id_seq RESTART WITH 1").executeUpdate();
                em.getTransaction().commit();
            } catch (Exception ignored) { }
        }
    }
}