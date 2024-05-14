package dat.controller;

import dat.dao.UserDAO;
import dat.dto.UserDTO;
import dat.exception.ApiException;
import dat.model.User;
import io.javalin.http.Context;

import java.util.Optional;

public class UserController extends Controller<User, UserDTO> {

    private final UserDAO dao;

    public UserController(UserDAO dao) {
        super(dao);
        this.dao = dao;
    }

    public void updateDepartmentOnUser(Context ctx) throws ApiException {
        this.validateId(ctx); // Will throw ApiException if id is invalid
        final String id = ctx.pathParam("id");
        Optional<User> user = dao.readById(Integer.parseInt(id));
        if (user.isEmpty()) {
            throw new ApiException(404, "User not found");
        }
        final User jsonRequest = ctx.bodyAsClass(this.dao.getClazz());
        user.get().setDepartment(jsonRequest.getDepartment());
        final User entity = this.dao.update(user.get());
        ctx.status(200);
        ctx.json(entity.toDTO());
    }

}