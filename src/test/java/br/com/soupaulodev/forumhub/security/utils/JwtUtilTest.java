package br.com.soupaulodev.forumhub.security.utils;

import br.com.soupaulodev.forumhub.modules.exception.usecase.TokenExpiredCustomException;
import br.com.soupaulodev.forumhub.modules.user.entity.UserEntity;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.test.util.ReflectionTestUtils;

import java.time.Instant;
import java.util.Date;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for the {@link JwtUtil} class.
 * <p>
 * This class contains unit tests for the methods in the {@link JwtUtil} class. The tests cover
 * the generation of JWT tokens, refresh tokens, extraction of usernames and user IDs from tokens,
 * and checking if a token has expired.
 * </p>
 *
 * @author <a href="https://soupaulodev.com.br">soupaulodev</a>
 */
class JwtUtilTest {

    private JwtUtil jwtUtil;

    @BeforeEach
    void setUp() {
        jwtUtil = new JwtUtil();
        ReflectionTestUtils.setField(jwtUtil, "issuer", "test-issuer");
        ReflectionTestUtils.setField(jwtUtil, "secretKey", "test-secret-key");
        ReflectionTestUtils.setField(jwtUtil, "expirationDate", 7);
    }

    @Test
    void testGenerateToken_Success() {
        UserEntity user = new UserEntity();
        user.setUsername("testuser");
        user.setId(UUID.randomUUID());

        String token = jwtUtil.generateToken(user);

        assertNotNull(token, "Token should not be null");
        DecodedJWT decodedJWT = JWT.decode(token);
        assertEquals("test-issuer", decodedJWT.getIssuer(), "Issuer should match");
        assertEquals(user.getId().toString(), decodedJWT.getSubject(), "Subject should match user ID");
        assertTrue(decodedJWT.getExpiresAt().after(new Date()), "Token expiration date should be in the future");
    }


    @Test
    void testGenerateRefreshToken_Success() {
        UserEntity user = new UserEntity();
        user.setUsername("testuser");

        String refreshToken = jwtUtil.generateRefreshToken(user);

        assertNotNull(refreshToken, "Refresh token should not be null");
        DecodedJWT decodedJWT = JWT.decode(refreshToken);
        assertEquals("test-issuer", decodedJWT.getIssuer(), "Issuer should match");
        assertEquals("testuser", decodedJWT.getSubject(), "Subject should match username");
        assertEquals("refresh", decodedJWT.getClaim("type").asString(), "Token type should be refresh");
        assertTrue(decodedJWT.getExpiresAt().after(new Date()), "Token expiration date should be in the future");
    }

    @Test
    void testExtractUsername_Success() {
        String token = JWT.create()
                .withIssuer("test-issuer")
                .withSubject("testuser")
                .withClaim("username", "testuser")
                .withExpiresAt(Date.from(Instant.now().plusSeconds(3600)))
                .sign(Algorithm.HMAC256("test-secret-key"));

        String username = jwtUtil.extractUsername(token);

        assertEquals("testuser", username, "Extracted username should match");
    }

    @Test
    void testIsTokenExpired_NotExpired() {
        String token = JWT.create()
                .withIssuer("test-issuer")
                .withSubject("testuser")
                .withExpiresAt(Date.from(Instant.now().plusSeconds(3600)))
                .sign(Algorithm.HMAC256("test-secret-key"));

        boolean isExpired = jwtUtil.isTokenExpired(token);

        assertFalse(isExpired, "Token should not be expired");
    }

    @Test
    void testIsTokenExpired_Expired() {
        String token = JWT.create()
                .withIssuer("test-issuer")
                .withSubject("testuser")
                .withExpiresAt(Date.from(Instant.now().minusSeconds(120000)))
                .sign(Algorithm.HMAC256("test-secret-key"));

        boolean isExpired;
        try {
            isExpired = jwtUtil.isTokenExpired(token);
        } catch (TokenExpiredCustomException e) {
            isExpired = true;
        }

        assertTrue(isExpired, "Token should be expired");
    }

    @Test
    void testDecodeToken_TokenExpired() {
        String token = JWT.create()
                .withIssuer("test-issuer")
                .withSubject("testuser")
                .withExpiresAt(Date.from(Instant.now().minusSeconds(3600)))
                .sign(Algorithm.HMAC256("test-secret-key"));

        TokenExpiredCustomException exception = assertThrows(
                TokenExpiredCustomException.class,
                () -> jwtUtil.extractUsername(token),
                "Should throw TokenExpiredCustomException");

        assertEquals("Token has expired. Please refresh.", exception.getMessage(), "Exception message should match");
    }

    @Test
    void testDecodeToken_InvalidToken() {
        String invalidToken = "invalid-token";

        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> jwtUtil.extractUsername(invalidToken),
                "Should throw IllegalArgumentException for invalid token");

        assertEquals("Invalid token.", exception.getMessage(), "Exception message should match");
    }

    @Test
    void testExtractUserId_Success() {
        String token = JWT.create()
                .withIssuer("test-issuer")
                .withSubject("12345")
                .withExpiresAt(Date.from(Instant.now().plusSeconds(3600)))
                .sign(Algorithm.HMAC256("test-secret-key"));

        String userId = jwtUtil.extractUserId(token);

        assertEquals("12345", userId, "Extracted user ID should match");
    }

    @Test
    void testDecodeToken_MalformedToken() {
        String malformedToken = "malformed-token";

        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> jwtUtil.extractUsername(malformedToken),
                "Should throw IllegalArgumentException for malformed token");

        assertEquals("Invalid token.", exception.getMessage(), "Exception message should match");
    }
}
