package dat.controller;

import dat.dao.SwapShiftsDAO;
import dat.dto.SwapShiftsDTO;
import dat.dto.SwapShiftsUserDTO;
import dat.model.SwapShifts;
import io.javalin.http.Context;

import java.util.List;

public class SwapShiftsController {

    private final SwapShiftsDAO swapShiftsDAO;

    public SwapShiftsController(SwapShiftsDAO swapShiftsDAO) {
        this.swapShiftsDAO = swapShiftsDAO;
    }

    public void getSwaps(Context ctx) {
        List<SwapShiftsUserDTO> swaps = swapShiftsDAO.getSwapShiftsWithUsernames();
        ctx.json(swaps);
    }

    public void createSwap(Context ctx) {
        SwapShiftsDTO dto = ctx.bodyAsClass(SwapShiftsDTO.class);
        SwapShifts swap = dto.toEntity();
        swapShiftsDAO.createSwap(swap);
        ctx.status(201).json(new SwapShiftsDTO(swap));
    }

    public void acceptSwap(Context ctx) {
        int swapId = Integer.parseInt(ctx.pathParam("id"));
        boolean isAccepted = Boolean.parseBoolean(ctx.queryParam("accepted"));
        swapShiftsDAO.updateSwapAcceptance(swapId, isAccepted ? "Approved" : "Not Approved");
        ctx.status(204);
    }
}
