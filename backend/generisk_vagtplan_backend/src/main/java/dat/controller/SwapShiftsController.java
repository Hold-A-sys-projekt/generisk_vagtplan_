package dat.controller;

import dat.dao.SwapShiftsDAO;
import dat.dto.SwapShiftsDTO;
import dat.model.SwapShifts;
import io.javalin.http.Context;

public class SwapShiftsController extends Controller<SwapShifts, SwapShiftsDTO> {

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
        String isAccepted = ctx.bodyAsClass(SwapShiftsDTO.class).getIsAccepted();

        try {
            dao.updateSwapAcceptance(swapId, isAccepted);
            ctx.status(200).result("Swap status updated to: " + isAccepted);
        } catch (RuntimeException e) {
            ctx.status(500).result("Error processing the swap: " + e.getMessage());
        }
    }

    public void createSwap(Context ctx) {
        SwapShiftsDTO swapShiftDTO = ctx.bodyAsClass(SwapShiftsDTO.class);
        try {
            SwapShifts swap = swapShiftDTO.toEntity();
            dao.createSwap(swap);
            ctx.status(201).result("Swap created with ID: " + swap.getId());
        } catch (RuntimeException e) {
            ctx.status(500).result("Error creating the swap: " + e.getMessage());
        }
    }
}