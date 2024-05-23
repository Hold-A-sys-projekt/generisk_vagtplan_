package dat.controller;

import com.google.gson.JsonObject;
import dat.security.TokenFactory;
import dat.dao.UserDAO;
import dat.dto.UserDTO;
import dat.dto.UserInfoDTO;
import dat.exception.ApiException;
import dat.exception.AuthorizationException;
import dat.model.User;
import io.javalin.http.Context;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import java.util.Optional;

public class UserController extends Controller<User, UserDTO> {

    private final UserDAO dao;
    private final TokenFactory tokenFactory = TokenFactory.getInstance();
    private static final Logger logger = (Logger) LoggerFactory.getLogger(UserController.class);

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

    public void login(Context ctx) throws AuthorizationException, ApiException {
        // get the user info from the request
        UserInfoDTO userInfo = ctx.bodyAsClass(UserInfoDTO.class);
        logger.info("Received login request for user: {}" + userInfo.username());

        // get the user from the database
        User user = dao.getVerifiedUser(userInfo);
        logger.info("User verified successfully: " + user.getUsername() + user.getRole().getName());

        // supply the user with a token
        String token = getToken(user.getUsername(), user.getRole().getName());
        logger.info("Generated token for user: " + user.getUsername());

        // verify the token before sending it back
        tokenFactory.verifyToken(token);
        logger.info("Token verified successfully");

        // response to client including username and token
        ctx.status(200);
        ctx.result(createResponse(user.getUsername(), user.getRole().getName(), token));
        logger.info("Login successful for user: " + user.getUsername());
    }

    public String getToken(String username, String role) throws ApiException {
        return tokenFactory.createToken(username, role);
    }

    private String createResponse(String username, String role, String token) {
        JsonObject responseJson = new JsonObject();
        responseJson.addProperty("username", username);
        responseJson.addProperty("role", role);
        responseJson.addProperty("token", token);
        return responseJson.toString();
    }
}