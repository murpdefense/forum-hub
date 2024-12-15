package br.com.soupaulodev.forumhub.modules.auth.controller;

import br.com.soupaulodev.forumhub.modules.auth.controller.dto.LoginRequestDTO;
import br.com.soupaulodev.forumhub.modules.auth.usecase.LoginUseCase;
import br.com.soupaulodev.forumhub.modules.auth.usecase.LogoutUseCase;
import br.com.soupaulodev.forumhub.modules.auth.usecase.SignUpUseCase;
import br.com.soupaulodev.forumhub.modules.user.controller.dto.UserCreateRequestDTO;
import jakarta.servlet.http.Cookie;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpServletResponse;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

/**
 * Unit tests for the {@link AuthController} class.
 * <p>
 *     The {@link AuthControllerTest} class contains unit tests for the {@link AuthController} class.
 *     It tests the login, registration, and logout of users using the {@link AuthController} class.
 * </p>
 * <p>
 *     The tests use Mockito to mock dependencies and verify the behavior of the authentication controller.
 * </p>
 *
 * @author <a href="https://soupaulodev.com.br">soupaulodev</a>
 */
class AuthControllerTest {

    @Mock
    private LoginUseCase loginUseCase;

    @Mock
    private SignUpUseCase signUpUseCase;

    @Mock
    private LogoutUseCase logoutUseCase;

    @InjectMocks
    private AuthController authController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void shouldLoginUserSuccessfully() {
        // Arrange
        LoginRequestDTO loginRequestDTO = new LoginRequestDTO("username", "password");
        Cookie cookie = new Cookie("JWT_TOKEN", "mock-token");
        when(loginUseCase.execute(any(LoginRequestDTO.class))).thenReturn(cookie);

        MockHttpServletResponse response = new MockHttpServletResponse();

        // Act
        ResponseEntity<String> result = authController.login(loginRequestDTO, response);

        // Assert
        assertEquals(200, result.getStatusCodeValue(), "Response status should be 200 OK");
        assertTrue(response.containsHeader("Set-Cookie"), "Response should contain Set-Cookie header");
        assertEquals("mock-token", response.getCookie("JWT_TOKEN").getValue(), "Cookie value should match the mocked token");
        assertEquals("User logged in successfully", result.getBody(), "Response body should confirm login success");
    }

    @Test
    void shouldRegisterUserSuccessfully() {
        // Arrange
        UserCreateRequestDTO requestDTO = new UserCreateRequestDTO(
                "Name Example",
                "Username Example",
                "email@example.com",
                "password");
        Cookie cookie = new Cookie("JWT_TOKEN", "mock-token");
        when(signUpUseCase.execute(any(UserCreateRequestDTO.class))).thenReturn(cookie);

        MockHttpServletResponse response = new MockHttpServletResponse();

        // Act
        ResponseEntity<String> result = authController.signUp(requestDTO, response);

        // Assert
        assertEquals(200, result.getStatusCodeValue(), "Response status should be 200 OK");
        assertTrue(response.containsHeader("Set-Cookie"), "Response should contain Set-Cookie header");
        assertEquals("mock-token", response.getCookie("JWT_TOKEN").getValue(), "Cookie value should match the mocked token");
        assertEquals("User registered successfully", result.getBody(), "Response body should confirm registration success");
    }

    @Test
    void shouldLogoutUserSuccessfully() {
        // Arrange
        Cookie logoutCookie = new Cookie("JWT_TOKEN", null);
        logoutCookie.setMaxAge(0);
        when(logoutUseCase.execute()).thenReturn(logoutCookie);

        MockHttpServletResponse response = new MockHttpServletResponse();

        // Act
        ResponseEntity<Void> result = authController.logout(response);

        // Assert
        assertEquals(200, result.getStatusCodeValue(), "Response status should be 200 OK");
        assertTrue(response.containsHeader("Set-Cookie"), "Response should contain Set-Cookie header for logout");
        assertNull(response.getCookie("JWT_TOKEN").getValue(), "Logout cookie value should be null");
    }
}
