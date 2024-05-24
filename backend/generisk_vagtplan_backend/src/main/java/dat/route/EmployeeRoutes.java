package dat.route;

import dat.controller.EmployeeController;
import dat.controller.ShiftController;
import dat.dao.EmployeeDAO;
import dat.dao.ShiftDAO;
import io.javalin.apibuilder.EndpointGroup;

import static io.javalin.apibuilder.ApiBuilder.*;

public class EmployeeRoutes implements Route{

    private final EmployeeController employeeController = new EmployeeController(EmployeeDAO.getInstance());

    @Override
    public String getBasePath() {
        return "/employees";
    }

    @Override
    public EndpointGroup getRoutes() {
        return () -> {
            path("/", () -> {
                get(employeeController::getAllNonDeleted);
                post(employeeController::createEmployee);
                path("/{id}", () -> {
                    get(employeeController::getById);
                    delete(employeeController::softDelete);
                    path("/shifts", () -> {
                        get("",employeeController::getEmployeeShifts);
                        get("/current",employeeController::getCurrentShift);
                    });
                });
            });
        };
    }
}
