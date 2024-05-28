package dat.model;

import com.fasterxml.jackson.annotation.*;
import dat.dto.UserDTO;
import jakarta.persistence.Entity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.mindrot.jbcrypt.BCrypt;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.LinkedHashSet;
import java.util.Set;

// soft delete
@Entity
@Table(name = "users")
@NamedQueries(@NamedQuery(name = "User.deleteAllRows", query = "DELETE FROM User"))
@Getter
@Setter
@NoArgsConstructor
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class User extends SoftDeletableEntity implements Serializable, dat.model.Entity<UserDTO> {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true, nullable = false)
    private Integer id;

    @Column(name = "email", unique = false, nullable = false)
    private String email;
//TODO: username should probably be their real name
    @Column(name = "username", unique = true, nullable = false, length = 25)
    private String username;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "created_on", nullable = false)
    private LocalDateTime createdOn;

    @Column(name = "updated_on", nullable = false)
    private LocalDateTime updatedOn;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "role_name", referencedColumnName = "role_name", nullable = false)
    private Role role;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)

    private final Set<Review> reviews = new LinkedHashSet<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.PERSIST, fetch = FetchType.EAGER)
    private Set<Shift> shifts = new LinkedHashSet<>();

    @ManyToOne(fetch = FetchType.EAGER)
    private Department department;

    @OneToOne
    private Company company;


    public User(String email, String username, String password) {
        this.email = email;
        this.username = username;
        this.password = BCrypt.hashpw(password, BCrypt.gensalt());
        this.createdOn = LocalDateTime.now();
        this.updatedOn = LocalDateTime.now();
        this.isDeleted = false;
    }

    //TODO: second constructor, delete if not used
    public User(String email, String username, String password, Role role, Department department) {
        this.email = email;
        this.username = username;
        this.password = BCrypt.hashpw(password, BCrypt.gensalt());
        this.createdOn = LocalDateTime.now();
        this.updatedOn = LocalDateTime.now();
        this.department = department;
        this.role = role;
        this.isDeleted = false;
    }

    public boolean checkPassword(String checkedPassword) {
        return BCrypt.checkpw(checkedPassword, this.password);
    }

    public void addReview(Review review) {
        this.reviews.add(review);
        review.setUser(this);
    }

    public void addShift(Shift shift) {
        this.shifts.add(shift);
        shift.setUser(this);
    }

    @Override
    public void setId(Object id) {
        if (id != null) {
            this.id = Integer.parseInt(id.toString());
        }
    }

    @Override
    public UserDTO toDTO() {
        return new UserDTO(this);
    }
}
