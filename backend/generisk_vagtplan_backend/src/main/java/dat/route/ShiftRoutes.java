package dat.route;

import dat.controller.BuyRequestController;
import dat.controller.ShiftController;
import dat.controller.UserController;
import dat.dao.BuyRequestDAO;
import dat.dao.ShiftDAO;
import dat.dao.UserDAO;
import io.javalin.apibuilder.EndpointGroup;

import static io.javalin.apibuilder.ApiBuilder.*;

public class ShiftRoutes implements Route {



    private final ShiftController shiftController = new ShiftController(ShiftDAO.getInstance());
    private final BuyRequestController buyRequestController = new BuyRequestController(BuyRequestDAO.getInstance());
    @Override
    public String getBasePath() {
        return "/shifts";
    }

    @Override
    public EndpointGroup getRoutes() {
        return () -> {
            path("/", () -> {
                get("/{status}", shiftController::getByStatus);
                get(shiftController::getAll);
                post(shiftController::post);
                path("/{id}", () -> {
                    get(shiftController::getById);
                    post("/punch-in" ,shiftController::punchIn);
                    post("/punch-out" ,shiftController::punchOut);
                    patch("/status", shiftController::updateShiftStatus);
                    post("/request", buyRequestController::createBuyRequest);
                });
            });
        };
    }

}
