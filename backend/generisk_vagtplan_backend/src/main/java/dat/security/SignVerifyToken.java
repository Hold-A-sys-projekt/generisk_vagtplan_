package dat.security;

import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jose.crypto.MACVerifier;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import dat.dto.UserDTO;
import dat.exception.AuthorizationException;

import java.text.ParseException;
import java.util.Date;

public class SignVerifyToken {

    private final String ISSUER;
    private final String TOKEN_EXPIRE_TIME;

    private final JWSSigner SIGNER;
    private final JWSVerifier VERIFIER;

    public SignVerifyToken(String issuer, String tokenExpireTime, String secretKey) {
        this.ISSUER = issuer;
        this.TOKEN_EXPIRE_TIME = tokenExpireTime;
        try {
            this.SIGNER = new MACSigner(secretKey.getBytes());
            this.VERIFIER = new MACVerifier(secretKey.getBytes());
        } catch (JOSEException e) {
            throw new RuntimeException("Signing failed", e);
        }
    }

    public String signToken(String username, Integer id, Date date) throws JOSEException {
        JWTClaimsSet claims = this.createClaims(username, id, date);
        JWSObject jwsObject = this.createHeaderAndPayload(claims);
        return this.signTokenWithSecretKey(jwsObject);
    }

    private JWTClaimsSet createClaims(String username, Integer id, Date date) {
        return new JWTClaimsSet.Builder()
                .subject(username)
                .issuer(ISSUER)
                .claim("username", username)
                .claim("userId", id)
                .expirationTime(new Date(date.getTime() + Integer.parseInt(TOKEN_EXPIRE_TIME)))
                .build();
    }

    private JWSObject createHeaderAndPayload(JWTClaimsSet claimsSet) {
        return new JWSObject(
                new JWSHeader(JWSAlgorithm.HS256),
                new Payload(claimsSet.toJSONObject())
        );
    }

    private String signTokenWithSecretKey(JWSObject jwsObject) {
        try {
            jwsObject.sign(SIGNER);
            return jwsObject.serialize();
        } catch (JOSEException e) {
            throw new RuntimeException("Signing failed", e);
        }
    }

    public SignedJWT parseTokenAndVerify(String token) throws ParseException, JOSEException, AuthorizationException {
        SignedJWT signedJWT = SignedJWT.parse(token);
        if (!signedJWT.verify(VERIFIER)) {
            throw new AuthorizationException(401, "Invalid token signature");
        }

        return signedJWT;
    }

    public UserDTO getJWTClaimsSet(JWTClaimsSet claimsSet) throws AuthorizationException {
        if (new Date().after(claimsSet.getExpirationTime())) {
            throw new AuthorizationException(401, "Token is expired");
        }

        String username = claimsSet.getClaim("username").toString();
        int id = Integer.parseInt(claimsSet.getClaim("userId").toString());
        return new UserDTO(username, id);
    }
}