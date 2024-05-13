package dat.controller;

import dat.dao.ReviewDAO;
import dat.dto.ReviewDTO;
import dat.model.Review;

public class ReviewController extends Controller<Review, ReviewDTO> {

    private final ReviewDAO dao;

    public ReviewController(ReviewDAO dao) {
        super(dao);
        this.dao = dao;
    }
}
