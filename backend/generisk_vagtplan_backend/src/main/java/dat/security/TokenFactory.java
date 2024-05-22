package dat.security;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;
import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jose.crypto.MACVerifier;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;

import dat.config.ApplicationConfig;
import dat.dto.UserDTO;
import dat.exception.ApiException;
import dat.exception.AuthorizationException;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.io.IOException;
import java.text.ParseException;
import java.util.Date;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class TokenFactory implements ITokenFactory {
    private static TokenFactory instance;
    private static final boolean isDeployed = (System.getenv("DEPLOYED") != null);
    private static final String ISSUER, TOKEN_EXPIRE_TIME, SECRET_KEY;

    static {
        try {
            ISSUER = isDeployed ? System.getenv("ISSUER") : ApplicationConfig.getProperty("issuer");
            TOKEN_EXPIRE_TIME = isDeployed ? System.getenv("TOKEN_EXPIRE_TIME") : ApplicationConfig.getProperty("token.expiration.time");
            SECRET_KEY = isDeployed ? System.getenv("SECRET_KEY") : ApplicationConfig.getProperty("secret.key");
        } catch (IOException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    public static TokenFactory getInstance() {
        if (instance == null) {
            instance = new TokenFactory();
        }
        return instance;
    }

    @Override
    public String createToken(String userName, String role) throws ApiException {
        try {
            Date date = new Date();
            return signToken(userName, role, date);
        } catch (JOSEException e) {
            throw new ApiException(500, "Could not create token");
        }
    }

    @Override
    public UserDTO verifyToken(String token) throws ApiException, AuthorizationException {
        try {
            SignedJWT signedJWT = parseTokenAndVerify(token);
            JWTClaimsSet claimsSet = signedJWT.getJWTClaimsSet();
            return getJWTClaimsSet(claimsSet);
        } catch (ParseException | JOSEException e) {
            throw new ApiException(401, e.getMessage());
        }
    }

    @Override
    public String[] parseJsonObject(String jsonString, Boolean tryLogin) throws ApiException {
        try {
            JsonObject json = JsonParser.parseString(jsonString).getAsJsonObject();
            String username = json.get("username").getAsString();
            String password = json.get("password").getAsString();
            return new String[]{username, password};
        } catch (JsonSyntaxException | NullPointerException e) {
            throw new ApiException(400, "Malformed JSON Supplied");
        }
    }

    private UserDTO getJWTClaimsSet(JWTClaimsSet claimsSet) throws AuthorizationException {
        if (new Date().after(claimsSet.getExpirationTime()))
            throw new AuthorizationException(401, "Token is expired");

        String username = claimsSet.getClaim("username").toString();
        String role = claimsSet.getClaim("role").toString();
        return new UserDTO(username, role);
    }

    private SignedJWT parseTokenAndVerify(String token) throws ParseException, JOSEException, AuthorizationException {
        SignedJWT signedJWT = SignedJWT.parse(token);
        JWSVerifier verifier = new MACVerifier(SECRET_KEY.getBytes());

        if (!signedJWT.verify(verifier)) {
            throw new AuthorizationException(401, "Invalid token signature");
        }
        return signedJWT;
    }

    private String signToken(String userName, String role, Date date) throws JOSEException {
        JWTClaimsSet claims = createClaims(userName, role, date);
        JWSObject jwsObject = createHeaderAndPayload(claims);
        return signTokenWithSecretKey(jwsObject);
    }

    private JWTClaimsSet createClaims(String username, String role, Date date) {
        return new JWTClaimsSet.Builder()
                .subject(username)
                .issuer(ISSUER)
                .claim("username", username)
                .claim("role", role)
                .expirationTime(new Date(date.getTime() + Integer.parseInt(TOKEN_EXPIRE_TIME)))
                .build();
    }

    private JWSObject createHeaderAndPayload(JWTClaimsSet claimsSet) {
        return new JWSObject(new JWSHeader(JWSAlgorithm.HS256), new Payload(claimsSet.toJSONObject()));
    }

    private String signTokenWithSecretKey(JWSObject jwsObject) {
        try {
            JWSSigner signer = new MACSigner(TokenFactory.SECRET_KEY.getBytes());
            jwsObject.sign(signer);
            return jwsObject.serialize();
        } catch (JOSEException e) {
            throw new RuntimeException("Signing failed", e);
        }
    }
}
