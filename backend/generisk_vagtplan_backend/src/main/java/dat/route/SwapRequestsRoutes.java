package dat.route;

import dat.controller.SwapRequestsController;
import dat.dao.SwapRequestsDAO;
import dat.dao.SwapShiftsDAO;
import io.javalin.apibuilder.EndpointGroup;

import static io.javalin.apibuilder.ApiBuilder.*;

public class SwapRequestsRoutes implements Route {

    private final SwapRequestsController swapRequestsController;

    public SwapRequestsRoutes() {
        SwapRequestsDAO swapRequestsDAO = SwapRequestsDAO.getInstance();
        SwapShiftsDAO swapShiftsDAO = SwapShiftsDAO.getInstance();
        this.swapRequestsController = new SwapRequestsController(swapRequestsDAO, swapShiftsDAO);
    }

    @Override
    public String getBasePath() {
        return "/swaprequests";
    }

    @Override
    public EndpointGroup getRoutes() {
        return () -> {
            path("", () -> {
                post(swapRequestsController::createRequest);
                path("{id}/approve", () -> {
                    post(swapRequestsController::acceptRequest);
                });
            });
        };
    }
}
