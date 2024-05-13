package dat.dao;

import dat.config.HibernateConfig;
import dat.model.Employee;
import dat.model.Manager;
import jakarta.persistence.EntityManager;

import jakarta.persistence.EntityManagerFactory;

import javax.management.relation.Role;

public class ManagerDAO extends DAO<Manager>{
    private static ManagerDAO INSTANCE;

    private ManagerDAO(EntityManagerFactory emf) {
        super(Manager.class, emf);
    }

    public static ManagerDAO getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new ManagerDAO(HibernateConfig.getEntityManagerFactory());
        }

        return INSTANCE;
    }
    public Employee addEmployee(Employee employee) {
        try (EntityManager em = emf.createEntityManager()) {
            em.getTransaction().begin();
            em.persist(employee);
            em.getTransaction().commit();
            return employee;
        }
    }

    public Employee getEmployee(int id) {
        try (EntityManager em = emf.createEntityManager()) {
            return em.find(Employee.class, id);
        }
    }

    public Employee updateEmployeeRole(Employee employee, String newRole) {
        try (EntityManager em = emf.createEntityManager()) {
            em.getTransaction().begin();

            Employee updatedEmployee = em.find(Employee.class, employee.getId());
            if (updatedEmployee != null) {
                updatedEmployee.setRole(newRole);
                em.merge(updatedEmployee);
            }
            em.getTransaction().commit();
            return updatedEmployee;
        }
    }
}
