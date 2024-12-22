package br.com.soupaulodev.forumhub.modules.auth.usecase;

import jakarta.servlet.http.Cookie;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test class for the LogoutUseCase class.
 * <p>
 * This class contains the test methods for the LogoutUseCase class.
 * It tests the behavior of the use case when logging out a user.
 * </p>
 *
 * @author <a href="https://soupaulodev.com.br">soupaulodev</a>
 */
class LogoutUseCaseTest {

    private LogoutUseCase logoutUseCase;

    @BeforeEach
    void setUp() {
        logoutUseCase = new LogoutUseCase();
    }

    @Test
    void execute_ReturnsExpiredCookie() {
        Cookie cookie = logoutUseCase.execute();

        assertNotNull(cookie, "Cookie should not be null");
        assertEquals("JWT_TOKEN", cookie.getName(), "Cookie name should be JWT_TOKEN");
        assertNull(cookie.getValue(), "Cookie value should be null");
        assertEquals(0, cookie.getMaxAge(), "Cookie should have max age set to 0 (expired)");
        assertTrue(cookie.isHttpOnly(), "Cookie should have HttpOnly set to true");
        assertTrue(cookie.getSecure(), "Cookie should have Secure flag set to true");
        assertEquals("/", cookie.getPath(), "Cookie should be accessible for the root path");
    }
}
