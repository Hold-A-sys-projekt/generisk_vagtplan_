package dat.controller;

import dat.config.HibernateConfig;
import dat.dao.DAO;
import dat.dao.ExampleDAO;
import dat.dao.SwapShiftsDAO;
import dat.dto.SwapShiftDTO;
import dat.model.SwapShifts;
import dat.model.User;
import io.javalin.http.Context;
import jakarta.persistence.EntityManager;

public class SwapShiftsController extends Controller<SwapShifts, SwapShiftDTO> {

    private final SwapShiftsDAO dao;

    public SwapShiftsController(SwapShiftsDAO dao) {
        super(dao);
        this.dao = dao;
    }

    public void getSwaps(Context ctx) {
        ctx.status(200);
        ctx.json(createFromEntities(dao.getSwapShifts()));
    }

    public void acceptSwap(Context ctx) {
        int swapId = Integer.parseInt(ctx.pathParam("id"));
        String isAccepted = ctx.formParam("isAccepted");

        EntityManager em = HibernateConfig.getEntityManagerFactory().createEntityManager();
        try {
            em.getTransaction().begin();

            SwapShifts swap = dao.readById(swapId).orElse(null);
            if (swap == null) {
                ctx.status(404).result("Swap not found");
                return;
            }

            swap.setIsAccepted(isAccepted);
            dao.update(swap);

            if ("Approved".equals(isAccepted)) {
                performSwap(swap, em);
            }

            em.getTransaction().commit();
            ctx.status(200).result("Swap status updated to: " + isAccepted);
        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            ctx.status(500).result("Error processing the swap: " + e.getMessage());
        } finally {
            em.close();
        }
    }

    private void performSwap(SwapShifts swap, EntityManager em) {
        try {
            User user1 = swap.getShift1().getUser();
            User user2 = swap.getShift2().getUser();

            // Swapping the users associated with each shift
            swap.getShift1().setUser(user2);
            swap.getShift2().setUser(user1);

            // Persist changes in the database
            em.merge(swap.getShift1());
            em.merge(swap.getShift2());

        } catch (Exception e) {
            throw new RuntimeException("Failed to perform shift swap", e);
        }
    }
}
