package dat.dto;

import dat.dao.DepartmentDAO;
import dat.model.Department;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class DepartmentDTO implements DTO<Department> {
    private int id;
    private String name;
    private CompanyDTO company;

    public DepartmentDTO(Department department) {
        this.id = department.getId();
        this.name = department.getName();
        this.company = department.getCompany().toDTO();
    }

    @Override
    public Department toEntity() {
        return DepartmentDAO.getInstance().readById(this.id).orElse(null);
    }
}
