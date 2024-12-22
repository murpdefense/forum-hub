package br.com.soupaulodev.forumhub.modules.auth.usecase;

import jakarta.servlet.http.Cookie;
import org.springframework.stereotype.Service;

/**
 * Handles the logout process by invalidating the user's JWT token.
 * <p>
 * The {@link LogoutUseCase} class manages the creation of a cookie with the name "JWT_TOKEN",
 * setting its value to null and its expiration time to 0, thereby logging the user out.
 * </p>
 * <p>
 * The generated cookie is marked as HttpOnly and Secure to enhance security,
 * ensuring it is transmitted only over HTTPS and cannot be accessed via JavaScript.
 * </p>
 *
 * <p><strong>Usage:</strong></p>
 * <pre>
 * LogoutUseCase logoutUseCase = new LogoutUseCase();
 * Cookie logoutCookie = logoutUseCase.execute();
 * </pre>
 * <p>This cookie can then be added to the HTTP response to log the user out.</p>
 *
 * @author <a href="https://soupaulodev.com.br">soupaulodev</a>
 */
@Service
public class LogoutUseCase {

    /**
     * Generates an expired cookie to invalidate the JWT token.
     * <p>
     * This method creates a new {@link Cookie} with the name "JWT_TOKEN",
     * null value, and an expiration time of 0 seconds, effectively removing it
     * from the client's browser upon inclusion in the HTTP response.
     * </p>
     *
     * @return A {@link Cookie} configured to log the user out.
     */
    public Cookie execute() {
        Cookie cookie = new Cookie("JWT_TOKEN", null);
        cookie.setHttpOnly(true);
        cookie.setSecure(true);
        cookie.setMaxAge(0);
        cookie.setPath("/");

        return cookie;
    }
}
