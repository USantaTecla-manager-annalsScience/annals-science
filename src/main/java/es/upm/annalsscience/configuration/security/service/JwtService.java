package es.upm.annalsscience.configuration.security.service;


import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import es.upm.annalsscience.configuration.security.exception.JwtException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class JwtService {
    public static final String BEARER = "Bearer ";

    private static final String EMAIL = "email";
    private static final String ISSUER = "annalsScience";
    private static final String ID = "id";
    private static final int EXPIRES_IN_MILLISECOND = 3600000;

    @Value("${secretToken}")
    private String SECRET;

    public String createToken(String user, long userId) {
        return JWT.create()
                .withIssuer(ISSUER)
                .withIssuedAt(new Date())
                .withNotBefore(new Date())
                .withExpiresAt(new Date(System.currentTimeMillis() + EXPIRES_IN_MILLISECOND))
                .withClaim(EMAIL, user)
                .withClaim(ID, userId)
                .sign(Algorithm.HMAC256(SECRET));
    }

    public boolean isBearer(String authorization) {
        return authorization != null && authorization.startsWith(BEARER) && authorization.split("\\.").length == 3;
    }

    public String user(String authorization) throws JwtException {
        return this.verify(authorization).getClaim(EMAIL).asString();
    }

    public String retrieveId(String authorization) throws JwtException {
        return this.verify(authorization).getClaim(ID).asString();
    }

    private DecodedJWT verify(String authorization) throws JwtException {
        if (!this.isBearer(authorization)) {
            throw new JwtException("It is not Berear");
        }
        try {
            return JWT.require(Algorithm.HMAC256(SECRET))
                    .withIssuer(ISSUER).build()
                    .verify(authorization.substring(BEARER.length()));
        } catch (Exception exception) {
            throw new JwtException("JWT is wrong. " + exception.getMessage());
        }
    }
}
