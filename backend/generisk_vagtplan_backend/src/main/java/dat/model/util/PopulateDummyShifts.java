package dat.model.util;

import dat.model.Shift;
import dat.model.User;
import dat.config.HibernateConfig;
import jakarta.persistence.EntityManager;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

public class PopulateDummyShifts {



    public static void persistDummyShifts() {
        ArrayList<String> days = new ArrayList<>();

        days.add("Monday");
        days.add("Tuesday");
        days.add("Wednesday");
        days.add("Thursday");
        days.add("Friday");
        days.add("Saturday");
        days.add("Sunday");

        EntityManager em = HibernateConfig.getEntityManagerFactory().createEntityManager();
        try {
            em.getTransaction().begin();


            for (int userId = 1; userId <= 5; userId++) {
                User user = em.find(User.class, userId);
                if (user != null) {
                    Shift shift = new Shift(
                            days.get(userId - 1) + " shift",
                            LocalDateTime.now().plusHours(1),
                            LocalDateTime.now().plusHours(9),
                            user
                    );
                    em.persist(shift);
                } else {
                    System.out.println("No user found with ID: " + userId);
                }
            }

            em.getTransaction().commit();
        } catch (Exception ex) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            ex.printStackTrace();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }
}
