package dat.route;

import io.javalin.apibuilder.EndpointGroup;

public interface Route {

    default String getBasePath() {
        return "/";
    }

    EndpointGroup getRoutes();
}