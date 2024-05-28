package dat.model;

import com.fasterxml.jackson.annotation.*;
import dat.dto.ReviewDTO;
import jakarta.persistence.*;
import jakarta.persistence.Entity;
import lombok.*;


@Entity
@Table(name = "reviews")
@NoArgsConstructor
@Getter
@Setter
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class Review implements dat.model.Entity<ReviewDTO> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true, nullable = false)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    @JsonIgnore
    private User user;

    @Column(name = "comment", nullable = false)
    private String comment;

    @Column(name = "rating", nullable = false)
    private int rating;

    public Review(User user, String comment, int rating) {
        this.user = user;
        this.comment = comment;
        this.rating = rating;
    }

    @Override
    public void setId(Object id) {
        this.id = (Integer) id;
    }

    @Override
    public ReviewDTO toDTO() {
        return new ReviewDTO(this);
    }
}