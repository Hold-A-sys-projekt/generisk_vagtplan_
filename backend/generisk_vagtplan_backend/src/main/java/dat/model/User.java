package dat.model;

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

@Entity
@Table(name = "users")
@NamedQueries(@NamedQuery(name = "User.deleteAllRows", query = "DELETE FROM User"))
@Getter
@NoArgsConstructor
public class User implements Serializable, dat.model.Entity<UserDTO> {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true, nullable = false)
    private Integer id;

    @Column(name = "email", unique = true, nullable = false)
    private String email;

    @Column(name = "username", unique = true, nullable = false, length = 25)
    @Setter
    private String username;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "created_on", nullable = false)
    @Setter
    private LocalDateTime createdOn;

    @Column(name = "updated_on", nullable = false)
    @Setter
    private LocalDateTime updatedOn;

    @Setter
    @JoinColumn(name = "role_name", referencedColumnName = "role_name", nullable = false)
    @ManyToOne(fetch = FetchType.EAGER)
    private RouteRoles role;

    @JoinColumn(name = "department_id", referencedColumnName = "department_id")
    @ManyToOne(fetch = FetchType.EAGER)
    private Department department;

    @JoinColumn(name = "company_id", referencedColumnName = "company_id")
    @OneToOne(fetch = FetchType.EAGER)
    private Company company;

    public User(String email, String username, String password) {
        this.email = email;
        this.setUsername(username);
        this.setPassword(password);
        this.setCreatedOn(LocalDateTime.now());
        this.setUpdatedOn(LocalDateTime.now());
    }

    public boolean checkPassword(String checkedPassword) {
        return BCrypt.checkpw(checkedPassword, this.password);
    }

    public void setPassword(String password) {
        this.password = BCrypt.hashpw(password, BCrypt.gensalt());
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