package dat.dto;

import dat.dao.UserDAO;
import dat.model.User;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.ZoneId;

@Getter
@ToString
@NoArgsConstructor
public class UserDTO implements DTO<User> {

    private String username;
    private String email;
    private Long createdOn;
    private Integer id;
    private DepartmentDTO department;
    private RoleDTO role;

    public UserDTO(String username, int id) {
        this.username = username;
        this.id = id;
    }

    public UserDTO(String username, Long createdOn, Integer id) {
        this.username = username;
        this.createdOn = createdOn;
        this.id = id;
    }

    public UserDTO(String username, Long createdOn, Integer id, String email) {
        this.username = username;
        this.createdOn = createdOn;
        this.id = id;
        this.email = email;
    }

    public UserDTO(String username, Long createdOn, Integer id, String email, DepartmentDTO department, RoleDTO role) {
        this.username = username;
        this.email = email;
        this.createdOn = createdOn;
        this.id = id;
        this.department = department;
        this.role = role;
    }

    public UserDTO(User user) {
        this(user.getUsername(),
                user.getCreatedOn().atZone(ZoneId.systemDefault()).toInstant().getEpochSecond() * 1000,
                user.getId(), user.getEmail(), user.getDepartment().toDTO(), user.getRole().toDTO());
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