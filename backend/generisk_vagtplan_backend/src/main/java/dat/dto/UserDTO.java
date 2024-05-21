package dat.dto;

import dat.dao.UserDAO;
import dat.model.User;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
@ToString
@NoArgsConstructor
public class UserDTO implements DTO<User> {

    private Integer id;
    private String email;
    private String username;
    private RoleDTO role;
    private LocalDateTime createdOn;
    private LocalDateTime updatedOn;
    private DepartmentDTO department;
    private boolean isDeleted;
    private LocalDateTime deletedOn;


    public UserDTO(String username, int id) {
        this.username = username;
        this.id = id;
    }

    public UserDTO(String username, String role) {
        this.username = username;
        this.role = new RoleDTO(role);
    }

    public UserDTO(String username, LocalDateTime createdOn, Integer id) {
        this.username = username;
        this.createdOn = createdOn;
        this.id = id;
    }

    public UserDTO(String username, LocalDateTime createdOn, Integer id, String email) {
        this.username = username;
        this.createdOn = createdOn;
        this.id = id;
        this.email = email;
    }

    public UserDTO(String username, LocalDateTime createdOn, Integer id, String email, DepartmentDTO department, RoleDTO role) {
        this.username = username;
        this.email = email;
        this.createdOn = createdOn;
        this.department = department;
        this.id = id;
        this.role = role;
    }

    // updatedOn is required by DB, therefore the below is here
    public UserDTO(String username, LocalDateTime createdOn, LocalDateTime updatedOn, Integer id, String email, DepartmentDTO department, RoleDTO role, boolean isDeleted, LocalDateTime deletedOn) {
        this.username = username;
        this.email = email;
        this.createdOn = createdOn;
        this.updatedOn = updatedOn;
        this.id = id;
        this.department = department;
        this.role = role;
        this.isDeleted = isDeleted;
        this.deletedOn = deletedOn;
    }

    public UserDTO(User user) {
        this(user.getUsername(),
                user.getCreatedOn(), user.getUpdatedOn(),
                user.getId(), user.getEmail(), (user.getDepartment() == null ? null : user.getDepartment().toDTO()), user.getRole().toDTO(), user.isDeleted(),
                user.getDeletedOn());
    }

    public void setId(String id) {
        this.id = Integer.parseInt(id);
    }

    @Override
    public User toEntity() {
        UserDAO userDAO = UserDAO.getInstance();
        return userDAO.readById(this.username).orElse(null);
    }

    public void setUpdatedOn(LocalDateTime updatedOn) {
        this.updatedOn = updatedOn;
    }
}
