package dat.dto;

import dat.dao.UserDAO;
import dat.model.User;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.time.ZoneId;

@Getter
@ToString
@NoArgsConstructor
public class UserDTO implements DTO<User> {

    private String username;
    private String description;
    private String email;
    private Long createdAt;
    private Integer id;

    public UserDTO(String username, int id) {
        this.username = username;
        this.id = id;
    }

    public UserDTO(String username, Long createdAt, Integer id) {
        this.username = username;
        this.createdAt = createdAt;
        this.id = id;
    }

    public UserDTO(String username, Long createdAt, Integer id, String email) {
        this.username = username;
        this.createdAt = createdAt;
        this.id = id;
        this.email = email;
    }

    public UserDTO(User user) {
        this(user.getUsername(),
                user.getCreatedOn().atZone(ZoneId.systemDefault()).toInstant().getEpochSecond() * 1000,
                user.getId(), user.getEmail());
    }

    public void setId(String id) {
        this.id = Integer.parseInt(id);
    }

    @Override
    public User toEntity() {
        UserDAO userDAO = UserDAO.getInstance();
        return userDAO.readById(this.username).orElse(null);
    }
}