package no.ntnu.fullstack.queues.authentication.jwt;

import io.jsonwebtoken.Jwts;
import org.springframework.context.annotation.Configuration;

/**
 * Abstracts the creation and decoding of JWTs.
 */
@Configuration
public class JwtUtil {

    private final JwtConfig jwtConfig;

    public JwtUtil(JwtConfig jwtConfig) {
        this.jwtConfig = jwtConfig;
    }

    /**
     * Creates access token with the configs defined in JwtConfig and adds the neccessary prefix to it
     * @param subject subject of jwt, in this case an email address
     * @return jwt as string with given configurations
     */
    public String createAccessToken(String subject) {
        return Jwts.builder()
                .setSubject(subject)
                .setExpiration(jwtConfig.accessTokenExpirationDate())
                .signWith(jwtConfig.getSecretKey())
                .compact();
    }

    /**
     * Creates a refresh token for the given subject with the configurations given from JwtConfig
     * @param subject subject of jwt, in this case an email address
     * @return jwt as string with the given configurations
     */
    public String createRefreshToken(String subject) {
        return Jwts.builder()
                .setSubject(subject)
                .setExpiration(jwtConfig.refreshTokenExpirationDate())
                .signWith(jwtConfig.getSecretKey())
                .compact();
    }

    public String cookieName() {
        return jwtConfig.getCookieName();
    }

    public String decode(String refreshToken) {
        return null;
    }
}
