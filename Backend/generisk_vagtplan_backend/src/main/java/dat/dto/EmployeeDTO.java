package dat.dto;

import dat.dao.EmployeeDAO;
import dat.model.Employee;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@ToString
@NoArgsConstructor
public class EmployeeDTO implements DTO<Employee> {
    private Integer id;
    private String employeename;
    private String role;


      public EmployeeDTO(Employee employee) {
            this.id = employee.getId();
            this.employeename = employee.getEmployeename();
            this.role = employee.getRole();
        }

    @Override
    public Employee toEntity() {
        EmployeeDAO employeeDAO = EmployeeDAO.getInstance();
        return employeeDAO.readById(this.employeename).orElse(null);
    }
}
