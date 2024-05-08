package dat.route;


import dat.controller.ReviewController;
import dat.dao.ReviewDAO;
import io.javalin.apibuilder.EndpointGroup;

import static io.javalin.apibuilder.ApiBuilder.*;

public class ReviewRoutes implements Route {
    private final ReviewController reviewController = new ReviewController(ReviewDAO.getInstance());

    @Override
    public String getBasePath() {
        return "/reviews";
    }

    @Override
    public EndpointGroup getRoutes() {
        return () -> {
            path("/getAll", () -> {
                get(reviewController::getAll);
            });
        };
    }
}
