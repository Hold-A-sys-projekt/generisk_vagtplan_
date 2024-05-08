package dat.route;

import dat.controller.ExampleController;
import dat.dao.ExampleDAO;
import io.javalin.apibuilder.EndpointGroup;

import static io.javalin.apibuilder.ApiBuilder.*;

public class ManagerRoutes {



    private final ExampleController exampleController = new ExampleController(ExampleDAO.getInstance());

    public String getBasePath() {
        return "/example";
    }


    public EndpointGroup getRoutes() {
        return () -> {

            get(exampleController::getAll);
            post(exampleController::post);
            path("/hello", () -> {
                get(exampleController::helloWorld);
            });
            path("/{id}", () -> {
                get(exampleController::getById);
                delete(exampleController::delete);
            });
        };
    }
}


