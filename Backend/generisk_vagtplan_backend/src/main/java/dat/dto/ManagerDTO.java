package dat.dto;

import dat.dao.ManagerDAO;
import dat.model.Employee;
import dat.model.Manager;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;

@Getter
@ToString
@NoArgsConstructor
public class ManagerDTO implements DTO<Manager> {
    private Integer id;
    private String managername;


    public ManagerDTO(Manager manager) {
        this.id = manager.getId();
        this.managername = manager.getManagername();
    }

    @Override
    public Manager toEntity() {
        ManagerDAO managerDAO = ManagerDAO.getInstance();
        return managerDAO.readById(this.managername).orElse(null);
    }




}

