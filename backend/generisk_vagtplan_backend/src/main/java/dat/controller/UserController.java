package dat.controller;

import dat.dao.UserDAO;
import dat.dto.UserDTO;
import dat.exception.ApiException;
import dat.message.Message;
import dat.model.User;
import io.javalin.http.Context;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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

    public void getUsersByRole(Context ctx) {
        String role = ctx.queryParam("role");
        System.out.println("Received role parameter: " + role);

        if (role == null || role.isEmpty()) {
            System.out.println("Role parameter is missing or empty");
            ctx.status(400).json(new Message(400, System.currentTimeMillis(), "Role parameter is required"));
            return;
        }

        try {
            List<User> users = dao.getUsersByRole(role);
            System.out.println("Fetched users: " + users);
            List<UserDTO> userDTOs = users.stream().map(User::toDTO).collect(Collectors.toList());
            ctx.json(userDTOs);
        } catch (Exception e) {
            System.err.println("Error fetching users by role: " + e.getMessage());
            ctx.status(500).json(new Message(500, System.currentTimeMillis(), "Internal server error"));
        }
    }





}