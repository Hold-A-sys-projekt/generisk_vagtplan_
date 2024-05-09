package dat.route;

import dat.controller.EmailController;

import io.javalin.apibuilder.EndpointGroup;

import static io.javalin.apibuilder.ApiBuilder.post;

public class EmailRoutes implements Route {

    private final EmailController emailController = new EmailController();

    @Override
    public String getBasePath() {
        return "/email";
    }

    @Override
    public EndpointGroup getRoutes()
    {
        return () -> {
            post("/send", emailController::sendEMail);
        };
    }
}