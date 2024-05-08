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

}
