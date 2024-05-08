package dat.route;

import dat.controller.RoleController;
import dat.dao.RoleDAO;
import io.javalin.apibuilder.EndpointGroup;
import static io.javalin.apibuilder.ApiBuilder.*;

public class RoleRoutes implements Route{
    private RoleController controller = new RoleController(RoleDAO.getInstance());

    @Override
    public String getBasePath() {
        return "/roles";
    }

    @Override
    public EndpointGroup getRoutes() {
        return () -> {
            get(controller::getAll);
        };
    }
}
