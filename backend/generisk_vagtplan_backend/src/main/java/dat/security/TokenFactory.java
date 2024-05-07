package dat.security;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nimbusds.jose.JOSEException;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import dat.dto.UserDTO;
import dat.dto.UserInfoDTO;
import dat.exception.ApiException;
import dat.exception.AuthorizationException;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.ParseException;
import java.util.Date;
import java.util.Map;

@SuppressWarnings({"rawtypes", "unchecked"})
public class TokenFactory {

    private static TokenFactory INSTANCE;

    private final Logger LOGGER = LoggerFactory.getLogger(TokenFactory.class);
    private final ObjectMapper OBJECT_MAPPER = new ObjectMapper();
    private final SignVerifyToken SIGNATURE;

    private TokenFactory() {
        String[] properties = getProperties();
        String issuer = properties[0];
        String tokenExpireTime = properties[1];
        String secretKey = properties[2];
        this.SIGNATURE = new SignVerifyToken(issuer, tokenExpireTime, secretKey);
    }

    public static TokenFactory getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new TokenFactory();
        }

        return INSTANCE;
    }

    public UserInfoDTO parseJsonObject(String jsonString) throws ApiException {
        try {
            Map json = OBJECT_MAPPER.readValue(jsonString, Map.class);
            String email = json.get("email").toString();
            String username = json.getOrDefault("username", "").toString();
            String password = json.get("password").toString();
            return new UserInfoDTO(email, username, password);
        } catch (JsonProcessingException | NullPointerException e) {
            throw new ApiException(400, "Malformed JSON Supplied");
        }
    }

    public String createToken(String username, Integer id) throws ApiException {
        try {
            return SIGNATURE.signToken(username, id, new Date());
        } catch (JOSEException e) {
            throw new ApiException(500, "Could not create token");
        }
    }

    public UserDTO verifyToken(String token) throws ApiException, AuthorizationException {
        try {
            SignedJWT signedJWT = SIGNATURE.parseTokenAndVerify(token);
            JWTClaimsSet claimsSet = signedJWT.getJWTClaimsSet();
            return SIGNATURE.getJWTClaimsSet(claimsSet);
        } catch (ParseException | JOSEException e) {
            throw new ApiException(401, e.getMessage());
        }
    }

    /**
     * Get properties from pom file
     *
     * @return String array with properties in order: issuer, token expiration time, secret key
     */
    @NotNull
    private String[] getProperties() {
        return new String[]{
                "cphbusiness.dk",
                "3600000",
                "841D8A6C80CBA4FCAD32D5367C18C53B"
        };
    }
}