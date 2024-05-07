package dat.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import dat.dao.UserDAO;
import dat.dto.UserInfoDTO;
import dat.exception.ApiException;
import dat.exception.AuthorizationException;
import dat.model.User;
import dat.security.TokenFactory;
import io.javalin.http.Context;
import lombok.NoArgsConstructor;

@NoArgsConstructor
public class AuthenticationController {

    private final UserDAO USER_DAO = UserDAO.getInstance();
    private final TokenFactory TOKEN_FACTORY = TokenFactory.getInstance();
    private final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    public void authenticate(Context ctx, boolean isRegister) throws AuthorizationException, ApiException {
        User user = getUser(ctx, isRegister);
        String token = TOKEN_FACTORY.createToken(user.getUsername(), user.getId());

        // Create response
        ctx.status(isRegister ? 201 : 200);
        ctx.result(createResponse(user.getUsername(), token));
    }

    private User getUser(Context ctx, boolean isRegister) throws ApiException, AuthorizationException {
        UserInfoDTO userInfo = TOKEN_FACTORY.parseJsonObject(ctx.body());
        return isRegister ? USER_DAO.registerUser(userInfo) : USER_DAO.getVerifiedUser(userInfo);
    }

    private String createResponse(String username, String token) {
        return OBJECT_MAPPER.createObjectNode()
                .put("username", username)
                .put("token", token)
                .toString();
    }
}