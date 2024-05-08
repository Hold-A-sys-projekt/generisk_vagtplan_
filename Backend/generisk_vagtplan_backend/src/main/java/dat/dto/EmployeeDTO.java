package dat.dto;

import dat.dao.EmployeeDAO;
import dat.model.Employee;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;

@Getter
@ToString
@NoArgsConstructor
public class EmployeeDTO implements DTO<Employee>{

    private Integer id;

    private String name;

    private List<ShiftDTO> shifts;

    public EmployeeDTO(Employee employee) {
        this.id = employee.getId();
        this.name = employee.getName();
        this.shifts = employee.getShifts().stream().map(ShiftDTO::new).toList();
    }


    @Override
    public Employee toEntity() {
        EmployeeDAO employeeDAO = EmployeeDAO.getInstance();
        return null;
    }
}
