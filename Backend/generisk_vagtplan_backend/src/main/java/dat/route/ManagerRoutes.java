package dat.route;

import dat.controller.ManagerController;
import dat.dao.ManagerDAO;
import dat.dto.EmployeeDTO;
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

    /*@Override
    public EndpointGroup getRoutes() {
        return () -> {
            path("/employees", () -> {
                post(ctx -> {
                    EmployeeDTO employeeDTO = ctx.bodyAsClass(EmployeeDTO.class);
                    managerController.addEmployee(ctx, employeeDTO);
                });
            });
        };
    }*/
    @Override
    public EndpointGroup getRoutes() {
        return () -> {
            path("/employees", () -> {
                post(managerController::addEmployee);
            });
        };
    }
}

