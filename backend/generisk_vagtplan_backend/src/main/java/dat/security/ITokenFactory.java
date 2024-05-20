package dat.security;

import dat.dto.UserDTO;
import dat.exception.ApiException;
import dat.exception.AuthorizationException;

import java.util.Set;

public interface ITokenFactory {
    String createToken(String userName) throws ApiException;
    UserDTO verifyToken(String token) throws ApiException, AuthorizationException;
    String[] parseJsonObject(String jsonString, Boolean tryLogin) throws ApiException;
}
