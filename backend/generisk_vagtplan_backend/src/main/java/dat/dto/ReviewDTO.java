package dat.dto;

import dat.model.Review;
import dat.model.User;
import lombok.AllArgsConstructor;
import lombok.Getter;


@AllArgsConstructor
@Getter
public class ReviewDTO implements DTO<Review> {

    private Integer id;
    private User user;
    private String comment;
    private int rating;

    public ReviewDTO(Review review) {
        this.id = review.getId();
        this.user = review.getUser();
        this.comment = review.getComment();
        this.rating = review.getRating();
    }

    @Override
    public Review toEntity() {
        ReviewDAO reviewDAO = ReviewDAO.getInstance();
        return reviewDAO.readById(this.id).orElse(null);
    }
}