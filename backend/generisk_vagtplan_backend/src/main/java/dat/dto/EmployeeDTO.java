package dat.dto;

import dat.dao.EmployeeDAO;
import dat.model.User;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;

@Getter
@ToString
@NoArgsConstructor
public class EmployeeDTO implements DTO<User>{

    private Integer id;

    private String name;

    private String role;

    private List<ShiftDTO> shifts;

    public EmployeeDTO(User user) {
        this.id = user.getId();
        this.name = user.getUsername();
        this.role = user.getRole().toString();
        this.shifts = null;

    }


    @Override
    public User toEntity() {
        EmployeeDAO employeeDAO = EmployeeDAO.getInstance();
        return employeeDAO.readById(this.id).orElse(null);
    }
}
//TODO: DELETE