package dat.dao;

import dat.config.HibernateConfig;
import dat.model.Status;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;

import org.junit.jupiter.api.AfterEach;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import dat.model.Shift;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ShiftDAOTest {
    private EntityManagerFactory emf;
    private ShiftDAO shiftDAO;
    private EntityManager em;

    @BeforeEach
    void setUp() {
        emf = HibernateConfig.getEntityManagerFactory();
        em = emf.createEntityManager();
        shiftDAO = ShiftDAO.getInstance();
    }
    @AfterEach
    void tearDown() {
        em.close();
    }


    @Test
    void getShiftsByEmployeeId() {
        int employeeId = 2;
        List<Shift> shifts = shiftDAO.getShiftsByUserId((employeeId));
        assertEquals(2, shifts.size());
    }

    @Test
    void getShiftStatus() {
        int shiftId = 2;
        Shift shift = shiftDAO.getShiftStatus(shiftId);
        assertEquals("FOR_SALE", shift.getStatus().toString());
        System.out.println(shift + " " + shift.getStatus());
    }

    @Test
    void updateShiftStatus() {
        int shiftId = 2;
        Shift shift = shiftDAO.getShiftStatus(shiftId);
        System.out.println(shift + " " + shift.getStatus().toString());
        shiftDAO.updateShiftStatus(shiftId, Status.COVERED);
        shift = shiftDAO.getShiftStatus(shiftId);
        assertEquals("COVERED", shift.getStatus().toString());
        System.out.println(shift + " " + shift.getStatus());
    }
}
