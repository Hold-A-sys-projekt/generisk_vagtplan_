package dat.controller;

import dat.dao.EmployeeDAO;
import dat.dao.UserDAO;
import dat.dto.ShiftDTO;
import dat.dto.UserDTO;
import dat.model.Shift;
import dat.model.User;
import io.javalin.http.Context;

import java.time.LocalDateTime;
import java.util.List;

public class UserController extends Controller<User, UserDTO> {

    private final UserDAO dao;

    public UserController(UserDAO dao) {
        super(dao);
        this.dao = dao;
    }

}