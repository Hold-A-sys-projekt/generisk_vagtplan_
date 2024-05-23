package dat.route;

import dat.controller.ManagerController;
import dat.dao.ManagerDAO;
import io.javalin.apibuilder.EndpointGroup;

import static io.javalin.apibuilder.ApiBuilder.*;

public class ManagerRoutes implements Route {

    private final ManagerController managerController = new ManagerController(ManagerDAO.getInstance());

    @Override
    public String getBasePath() {
        {
            return "/managers";
        }
    }

    @Override
    public EndpointGroup getRoutes() {
        return () -> {

            // http://localhost:7070/api/managers/employees/${userId}/shifts/${id}

            path("/employees", () -> {
                get(managerController::getAllEmployees);
                put("{userid}/shifts/{id}", managerController::updateShift);
                post(managerController::addEmployee);
            });
            path("/", () -> {
                get(managerController::getAllNonDeleted);
                path("/{id}", () -> {
                    get(managerController::getById);
                    delete(managerController::softDelete);
                });
            });
            path("employees/{id}", () -> {
                put(managerController::updateEmployeeRole);
            });
        };
    }
}
