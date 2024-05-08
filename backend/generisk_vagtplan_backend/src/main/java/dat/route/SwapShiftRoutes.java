package dat.route;

import dat.controller.SwapShiftsController;
import dat.dao.DAO;
import dat.dao.SwapShiftsDAO;
import io.javalin.apibuilder.EndpointGroup;

import static io.javalin.apibuilder.ApiBuilder.*;

public class SwapShiftRoutes implements Route {

    private final SwapShiftsController swapShiftsController = new SwapShiftsController(SwapShiftsDAO.getInstance());

    @Override
    public String getBasePath() {
        return "/swapshifts";
    }

    @Override
    public EndpointGroup getRoutes() {
        return () -> {
            path("", () -> {
                get(swapShiftsController::getSwaps);
                path("{id}", () -> {
                    post(swapShiftsController::acceptSwap);
                });

            });
        };
    }
}



