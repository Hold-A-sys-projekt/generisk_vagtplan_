package dat.util;

import dat.config.HibernateConfig;
import dat.model.SoftDeletableEntity;
import jakarta.persistence.*;
import jakarta.persistence.metamodel.EntityType;
import jakarta.persistence.metamodel.Metamodel;
import lombok.Setter;
import org.slf4j.LoggerFactory;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

// TODO: TEST
/**
 * This class is responsible for cleaning up the database by deleting soft-deleted entities that are older than a certain
 * amount of time
 */
public class DBHouseKeeper
{
    @Setter
    private int vacuumInterval = 6; // in months
    private EntityManagerFactory emf;
    private static final org.slf4j.Logger LOGGER = LoggerFactory.getLogger(DBHouseKeeper.class);

    public DBHouseKeeper() {
        this.emf = HibernateConfig.getEntityManagerFactory();
    }

    public static void main(String[] args)
    {
        DBHouseKeeper houseKeeper = new DBHouseKeeper();
        houseKeeper.vacuum();
    }

    public void vacuum()
    {
        List<Class<?>> softDeletableClasses = scanForSoftDeletableClasses();
        EntityManager em = emf.createEntityManager();
        LocalDateTime nMonths = LocalDateTime.now().minusMonths(vacuumInterval);

        for (Class<?> clazz : softDeletableClasses) {
            em.getTransaction().begin();
            Query query = em.createQuery("DELETE FROM " + clazz.getSimpleName() + " e WHERE e.isDeleted = true AND e.deletedOn < :nMonths");
            query.setParameter("nMonths", LocalDate.from(nMonths).atStartOfDay());
            int deletedCount = query.executeUpdate();
            em.getTransaction().commit();
            LOGGER.warn("Deleted {} rows from {} table", deletedCount, clazz.getSimpleName());
        }
    }

    private List<Class<?>> scanForSoftDeletableClasses() {
        List<Class<?>> softDeletableClasses = new ArrayList<>();
        EntityManagerFactory entityManagerFactory = HibernateConfig.getEntityManagerFactory();
        Metamodel metamodel = entityManagerFactory.getMetamodel();

        // Get all entity types
        Set<EntityType<?>> entities = metamodel.getEntities();

        // Get soft-deletable classes
        for (EntityType<?> entityType : entities) {
            Class<?> clazz = entityType.getJavaType();
            if (SoftDeletableEntity.class.isAssignableFrom(clazz)) {
                softDeletableClasses.add(clazz);
            }
        }

        return softDeletableClasses;
    }
}
