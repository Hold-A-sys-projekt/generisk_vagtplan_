package dat.controller;

import dat.dao.UserDAO;
import dat.dto.UserDTO;
import dat.exception.ApiException;
import dat.model.User;
import dat.util.EmailSender;
import dat.util.PasswordGenerator;
import io.javalin.http.Context;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Optional;

public class UserController extends Controller<User, UserDTO> {

    private final UserDAO dao;

    public UserController(UserDAO dao) {
        super(dao);
        this.dao = dao;
    }

    public void updateDepartment(Context ctx) throws ApiException {
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

    public void updateRole(Context ctx) throws ApiException {
        this.validateId(ctx); // Will throw ApiException if id is invalid
        final String id = ctx.pathParam("id");
        Optional<User> user = dao.readById(Integer.parseInt(id));
        if (user.isEmpty()) {
            throw new ApiException(404, "User not found");
        }
        final User jsonRequest = ctx.bodyAsClass(this.dao.getClazz());
        user.get().setRole(jsonRequest.getRole());
        final User entity = this.dao.update(user.get());
        ctx.status(200);
        ctx.json(entity.toDTO());
    }

    public void resetPassword(Context ctx) throws ApiException {
        this.validateId(ctx); // Will throw ApiException if id is invalid
        final String id = ctx.pathParam("id");
        Optional<User> user = dao.readById(Integer.parseInt(id));
        if (user.isEmpty()) {
            throw new ApiException(404, "User not found");
        }
        final String Password = PasswordGenerator.passwordGenerator();
        final User entity = user.get();
        entity.setPassword(Password);
        final User updatedEntity = this.dao.update(entity);
        EmailSender.sendEmail(entity.getEmail(), "Password Reset", List.of("Your password have been reset", "Your new password is: " + Password, "", "If this wasn't you, please contact support"), false);
        ctx.status(200);
        ctx.json(updatedEntity.toDTO());
    }
}