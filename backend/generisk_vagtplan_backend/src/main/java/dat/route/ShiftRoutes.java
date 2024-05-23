package dat.route;

import dat.controller.ShiftController;
import dat.controller.UserController;
import dat.dao.ShiftDAO;
import dat.dao.UserDAO;
import io.javalin.apibuilder.EndpointGroup;

import static io.javalin.apibuilder.ApiBuilder.*;

public class    ShiftRoutes implements Route{



    private final ShiftController shiftController = new ShiftController(ShiftDAO.getInstance());

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
                    get("/id", shiftController::getById);
                    get("/userid", shiftController::getShiftsByEmployeeId);
                    post("/punch-in" ,shiftController::punchIn);
                    post("/punch-out" ,shiftController::punchOut);
                    //post("/set-for-sale", shiftController::setForSale);
                    put("/status", shiftController::updateShiftStatus);

                });
            });
        };
    }

}
