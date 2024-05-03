package dat.route;

import dat.controller.AuthenticationController;
import io.javalin.apibuilder.EndpointGroup;

import static io.javalin.apibuilder.ApiBuilder.path;
import static io.javalin.apibuilder.ApiBuilder.post;

public class AuthenticationRoutes implements Route {

    private final AuthenticationController authenticationController = new AuthenticationController();

    @Override
    public String getBasePath() {
        return "/auth";
    }

    @Override
    public EndpointGroup getRoutes() {
        return () -> {
            path("/login", () -> post(ctx -> authenticationController.authenticate(ctx, false)));
            path("/register", () -> post(ctx -> authenticationController.authenticate(ctx, true)));
        };
    }
}