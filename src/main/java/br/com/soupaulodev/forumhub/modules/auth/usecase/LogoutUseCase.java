package br.com.soupaulodev.forumhub.modules.auth.usecase;

import jakarta.servlet.http.Cookie;
import org.springframework.stereotype.Service;

/**
 * Use case responsible for handling the logout process in the application.
 * <p>
 * The {@link LogoutUseCase} class processes the logout request by invalidating the JWT token cookie.
 * It generates a cookie with the name "JWT_TOKEN" and sets its expiration time to 0, effectively logging the user out.
 * </p>
 * <p>
 * The cookie is also marked as HttpOnly and Secure to ensure it is only sent over secure connections and cannot be accessed via JavaScript.
 * </p>
 *
 * @author <a href="https://soupaulodev.com.br">soupaulodev</a>
 */
@Service
public class LogoutUseCase {

    /**
     * Executes the logout process by invalidating the JWT token stored in the user's cookies.
     * <p>
     * This method creates a cookie with the same name as the JWT token, sets its value to null,
     * and sets its expiration to 0, effectively removing the token from the client's browser.
     * </p>
     *
     * @return A {@link Cookie} with the "JWT_TOKEN" name and an expired value, effectively logging out the user.
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
