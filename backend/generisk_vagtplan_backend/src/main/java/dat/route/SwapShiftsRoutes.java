package dat.route;

import dat.controller.SwapShiftsController;
import dat.dao.SwapShiftsDAO;
import io.javalin.apibuilder.EndpointGroup;

import static io.javalin.apibuilder.ApiBuilder.*;

public class SwapShiftsRoutes implements Route {

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
                post(swapShiftsController::createSwap);
                path("{id}/approve", () -> {
                    post(swapShiftsController::acceptSwap);
                });
            });
        };
    }
}
