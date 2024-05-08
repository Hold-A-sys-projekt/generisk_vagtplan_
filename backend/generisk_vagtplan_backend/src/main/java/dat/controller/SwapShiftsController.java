package dat.controller;

import dat.dao.DAO;
import dat.dao.ExampleDAO;
import dat.dao.SwapShiftsDAO;
import dat.dto.SwapShiftDTO;
import dat.model.SwapShifts;
import io.javalin.http.Context;

public class SwapShiftsController extends Controller<SwapShifts, SwapShiftDTO> {

    private final SwapShiftsDAO dao;

    public SwapShiftsController(SwapShiftsDAO dao) {
        super(dao);
        this.dao = dao;

    }

    public void acceptSwap(Context ctx) {
        int swapId = Integer.parseInt(ctx.pathParam("id"));
        SwapShifts swap = dao.readById(swapId).orElse(null);
        if (swap == null) {
            ctx.status(404);
            ctx.result("Swap not found");
            return;
        }
        swap.setIsAccepted("true");
        dao.update(swap);
        ctx.status(200);
        ctx.result("Swap accepted");
    }


    public void getSwaps(Context ctx) {
        ctx.status(200);
        ctx.json(createFromEntities(dao.getSwapShifts()));
    }
}
