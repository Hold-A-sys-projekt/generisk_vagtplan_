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
    private String description;
    private String email;
    private Long createdAt;
    private Integer id;
    private boolean isDeleted;
    private DepartmentDTO department;
    private Long deletedOn;
    private RoleDTO role;

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

    public UserDTO(String username, Long createdAt, Integer id, String email, DepartmentDTO department, RoleDTO role) {
        this.username = username;
        this.email = email;
        this.createdAt = createdAt;
        this.department = department;
        this.id = id;
        this.role = role;
    }

    // For testing purposes
    public UserDTO(String username, Long createdAt, Integer id, String email, DepartmentDTO department, RoleDTO role, boolean isDeleted, Long deletedOn) {
        this.username = username;
        this.email = email;
        this.createdAt = createdAt;
        this.id = id;
        this.department = department;
        this.role = role;
        this.isDeleted = isDeleted;
        this.deletedOn = deletedOn;
    }

    public UserDTO(User user) {
        this(user.getUsername(),
                user.getCreatedOn().atZone(ZoneId.systemDefault()).toInstant().getEpochSecond() * 1000,
                user.getId(), user.getEmail(), user.getDepartment().toDTO(), user.getRole().toDTO(), user.isDeleted(),
                user.getDeletedOn() == null ? null : user.getDeletedOn().atZone(ZoneId.systemDefault()).toInstant().getEpochSecond() * 1000);
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