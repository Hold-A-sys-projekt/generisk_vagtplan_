package dat.controller;

import dat.dao.DAO;
import dat.dto.DepartmentDTO;
import dat.model.Department;

public class DepartmentController extends Controller<Department, DepartmentDTO> {
    public DepartmentController(DAO<Department> dao) {
        super(dao);
    }
}
