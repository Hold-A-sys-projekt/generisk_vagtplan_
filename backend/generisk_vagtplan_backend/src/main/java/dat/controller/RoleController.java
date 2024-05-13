package dat.controller;

import dat.dao.DAO;
import dat.dto.RouteRolesDTO;
import dat.model.RouteRoles;

public class RoleController extends Controller<RouteRoles, RouteRolesDTO>{
    public RoleController(DAO<RouteRoles> dao) {
        super(dao);
    }
}
