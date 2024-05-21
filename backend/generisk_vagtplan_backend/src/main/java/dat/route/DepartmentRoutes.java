package dat.route;

import dat.controller.DepartmentController;
import dat.dao.DepartmentDAO;
import io.javalin.apibuilder.EndpointGroup;

import static io.javalin.apibuilder.ApiBuilder.*;

public class DepartmentRoutes implements Route{
    private final DepartmentController controller = new DepartmentController(DepartmentDAO.getInstance());

    @Override
    public String getBasePath() {
        return "/departments";
    }

    @Override
    public EndpointGroup getRoutes() {
        return () -> {
            path("/{id}", () -> {
                get(controller::getById);
                delete(controller::softDelete);
            });
            get(controller::getAllNonDeleted);
        };
    }
}
