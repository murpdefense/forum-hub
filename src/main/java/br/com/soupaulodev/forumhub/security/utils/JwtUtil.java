package br.com.soupaulodev.forumhub.security.utils;

import br.com.soupaulodev.forumhub.modules.exception.usecase.TokenExpiredCustomException;
import br.com.soupaulodev.forumhub.modules.user.entity.UserEntity;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.Instant;
import java.util.Date;

/**
 * Utility class for handling JWT (JSON Web Token) operations.
 * <p>
 * This class provides methods to generate JWT tokens, refresh tokens, extract usernames from tokens,
 * check if a token has expired, and decode the token. It utilizes the JWT library to create, verify,
 * and decode tokens based on a secret key and expiration date configured in application properties.
 * </p>
 *
 * The class also includes error handling, specifically for expired tokens, which is handled by
 * throwing a custom exception {@link TokenExpiredCustomException}.
 *
 * <p>
 * The JWT tokens generated include an expiration date, and the refresh token is configured with
 * a longer expiration time (30 days). The token is signed using the HMAC256 algorithm with a secret
 * key defined in the application properties.
 * </p>
 *
 * @author soupaulodev
 */
@Component
public class JwtUtil {

    private static final Logger logger = LoggerFactory.getLogger(JwtUtil.class);

    @Value("${jwt.issuer}")
    private String issuer;

    @Value("${jwt.secret}")
    private String secretKey;

    @Value("${jwt.expiration}")
    private int expirationDate;

    /**
     * Generates a JWT token for the specified user.
     * <p>
     * The token contains the username as the subject, the issuer, and an expiration date
     * calculated based on the configured expiration time.
     * </p>
     *
     * @param user The user for whom the token is generated.
     * @return The generated JWT token as a string.
     */
    public String generateToken(UserEntity user) {
        Algorithm algorithm = Algorithm.HMAC256(secretKey);
        return JWT.create()
                .withIssuer(issuer)
                .withSubject(user.getUsername())
                .withExpiresAt(generateExpirationDate())
                .sign(algorithm);
    }

    /**
     * Generates a refresh token for the specified user.
     * <p>
     * The refresh token contains the username as the subject, the issuer, and a longer
     * expiration time of 30 days. It also includes a custom claim to indicate that the token is a refresh token.
     * </p>
     *
     * @param user The user for whom the refresh token is generated.
     * @return The generated refresh token as a string.
     */
    public String generateRefreshToken(UserEntity user) {
        Algorithm algorithm = Algorithm.HMAC256(secretKey);
        return JWT.create()
                .withIssuer(issuer)
                .withSubject(user.getUsername())
                .withExpiresAt(Date.from(Instant.now().plus(Duration.ofDays(30)))) // 30 dias
                .withClaim("type", "refresh")
                .sign(algorithm);
    }

    /**
     * Extracts the username from the given JWT token.
     * <p>
     * This method decodes the token and retrieves the subject (username) from it.
     * </p>
     *
     * @param token The JWT token.
     * @return The username extracted from the token.
     */
    public String extractUsername(String token) {
        return decodeToken(token).getSubject();
    }

    /**
     * Checks if the given JWT token has expired.
     * <p>
     * This method checks the expiration date of the token and compares it to the current date.
     * </p>
     *
     * @param token The JWT token.
     * @return {@code true} if the token is expired, {@code false} otherwise.
     */
    public boolean isTokenExpired(String token) {
        return decodeToken(token).getExpiresAt().before(new Date());
    }

    /**
     * Decodes the given JWT token and verifies its authenticity.
     * <p>
     * This method decodes the token, verifies its signature, and checks for expiration.
     * If the token is expired, it throws a {@link TokenExpiredCustomException}. If the token is invalid,
     * it throws an {@link IllegalArgumentException}.
     * </p>
     *
     * @param token The JWT token to decode.
     * @return The decoded JWT token.
     * @throws TokenExpiredCustomException If the token is expired.
     * @throws IllegalArgumentException If the token is invalid.
     */
    private DecodedJWT decodeToken(String token) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(secretKey);
            JWTVerifier verifier = JWT.require(algorithm)
                    .withIssuer(issuer)
                    .build();
            return verifier.verify(token);
        } catch (TokenExpiredException e) {
            logger.warn("Token expired: {}", token);
            throw new TokenExpiredCustomException("Token has expired. Please refresh.");
        } catch (Exception e) {
            logger.error("Invalid token: {}", token, e);
            throw new IllegalArgumentException("Invalid token.");
        }
    }

    /**
     * Generates the expiration date for the JWT token.
     * <p>
     * The expiration date is calculated based on the configured expiration time (in days).
     * </p>
     *
     * @return The expiration date of the token.
     */
    private Date generateExpirationDate() {
        return Date.from(Instant.now().plus(Duration.ofDays(expirationDate)));
    }
}
