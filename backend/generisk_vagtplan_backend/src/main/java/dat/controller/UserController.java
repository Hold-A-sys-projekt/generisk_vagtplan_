package dat.controller;

import dat.dao.UserDAO;
import dat.dto.ShiftDTO;
import dat.dto.UserDTO;
import dat.dto.UserInfoDTO;
import dat.exception.AuthorizationException;
import dat.model.Shift;
import dat.model.User;

import io.javalin.http.Context;
import java.util.List;

public class UserController extends Controller<User, UserDTO> {

    private final UserDAO dao;

    public UserController(UserDAO dao) {
        super(dao);
        this.dao = dao;
    }
    public void getEmployeeShifts(Context ctx) {

        int employeeId = Integer.parseInt(ctx.pathParam("id"));

        User employee = dao.readById(employeeId).orElse(null);

        if (employee == null) {
            ctx.status(404);
            return;
        }

        List<ShiftDTO> shifts = employee.getShifts().stream()
                .map(Shift::toDTO)
                .toList();
        ctx.json(shifts);
    }

    public void create (Context ctx) throws AuthorizationException {
        UserInfoDTO jsonRequest = ctx.bodyAsClass(UserInfoDTO.class);
        User user = UserDAO.getInstance().registerUser(jsonRequest);
        ctx.status(201);
        ctx.json(user.toDTO());
    }
}