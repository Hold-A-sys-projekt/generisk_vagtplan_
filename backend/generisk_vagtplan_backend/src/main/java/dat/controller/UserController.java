package dat.controller;

import dat.dao.UserDAO;
import dat.dto.UserDTO;
import dat.model.User;
import io.javalin.http.Context;

public class UserController extends Controller<User, UserDTO> {

    private final UserDAO dao;

    public UserController(UserDAO dao) {
        super(dao);
        this.dao = dao;
    }
}