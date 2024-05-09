package dat.controller;
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

    public void getSwaps(Context ctx) {
        ctx.status(200);
        ctx.json(createFromEntities(dao.getSwapShifts()));
    }

    public void acceptSwap(Context ctx) {
        int swapId = Integer.parseInt(ctx.pathParam("id"));
        String isAccepted = ctx.bodyAsClass(SwapShiftDTO.class).getIsAccepted();

        try {
            dao.updateSwapAcceptance(swapId, isAccepted);
            ctx.status(200).result("Swap status updated to: " + isAccepted);
        } catch (RuntimeException e) {
            ctx.status(500).result("Error processing the swap: " + e.getMessage());
        }
    }
}

