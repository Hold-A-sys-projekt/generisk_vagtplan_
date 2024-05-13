package dat.controller;

import dat.dao.DAO;
import dat.dto.RoleDTO;
import dat.model.Role;

public class RoleController extends Controller<Role, RoleDTO>{
    public RoleController(DAO<Role> dao) {
        super(dao);
    }
}
