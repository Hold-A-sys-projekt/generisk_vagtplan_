package dat.route;

import dat.controller.CompanyController;
import dat.dao.CompanyDAO;
import io.javalin.apibuilder.EndpointGroup;

import static io.javalin.apibuilder.ApiBuilder.*;

public class CompanyRoutes implements Route {
    private final CompanyController companyController = new CompanyController(CompanyDAO.getInstance());

    @Override
    public String getBasePath() {
        return "/companies";
    }

    @Override
    public EndpointGroup getRoutes() {
        return () -> {
            path("/create", () -> {
                post(companyController::post);
            });
        };
    }


}