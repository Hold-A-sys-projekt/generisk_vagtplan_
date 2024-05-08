package dat.dao;

import dat.model.Review;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;

import java.util.List;

public class ReviewDAO extends DAO<Review> {
    private static ReviewDAO INSTANCE;
    private final dat.dao.DAO<dat.model.RouteRoles> ROLE_DAO = new dat.dao.DAO<>(dat.model.RouteRoles.class, emf);

    private ReviewDAO(EntityManagerFactory emf) {
        super(Review.class, emf);
    }

    public static ReviewDAO getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new ReviewDAO(dat.config.HibernateConfig.getEntityManagerFactory());
        }

        return INSTANCE;
    }

    @Override
    public List<Review> readAll() {
        return super.readAll();
    }
}
