package dat.config;

import dat.model.Employee;
import jakarta.persistence.EntityManagerFactory;

public class populate {
    public static void main(String[] args) {
        EntityManagerFactory emf = HibernateConfig.getEntityManagerFactory();

        try (var em = emf.createEntityManager()){
            em.getTransaction().begin();









        }




}


}
