package br.com.soupaulodev.forumhub.modules.auth.controller;

import br.com.soupaulodev.forumhub.modules.auth.controller.dto.LoginRequestDTO;
import br.com.soupaulodev.forumhub.modules.auth.usecase.LoginUseCase;
import br.com.soupaulodev.forumhub.modules.auth.usecase.LogoutUseCase;
import br.com.soupaulodev.forumhub.modules.auth.usecase.SignUpUseCase;
import br.com.soupaulodev.forumhub.modules.user.controller.dto.OwnerOfDTO;
import br.com.soupaulodev.forumhub.modules.user.controller.dto.ParticipatesInDTO;
import br.com.soupaulodev.forumhub.modules.user.controller.dto.UserCreateRequestDTO;
import br.com.soupaulodev.forumhub.modules.user.controller.dto.UserDetailsResponseDTO;
import jakarta.servlet.http.Cookie;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.security.core.context.SecurityContextHolder;

import java.time.Instant;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

/**
 * Unit tests for the {@link AuthController} class.
 * <p>
 * The {@link AuthControllerTest} class contains unit tests for the {@link AuthController} class.
 * It tests the login, registration, and logout of users using the {@link AuthController} class.
 * </p>
 * <p>
 * The tests use Mockito to mock dependencies and verify the behavior of the authentication controller.
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

    @AfterEach
    void tearDown() {
        SecurityContextHolder.clearContext();
    }

    @Test
    void shouldLoginUserSuccessfully() {
        LoginRequestDTO loginRequestDTO = new LoginRequestDTO("username", "password");
        Cookie cookie = new Cookie("JWT_TOKEN", "mock-token");
        when(loginUseCase.execute(any(LoginRequestDTO.class))).thenReturn(cookie);

        MockHttpServletResponse response = new MockHttpServletResponse();

        ResponseEntity<String> result = authController.login(loginRequestDTO, response);

        assertEquals(200, result.getStatusCode().value(), "Response status should be 200 OK");
        assertTrue(response.containsHeader("Set-Cookie"), "Response should contain Set-Cookie header");
        assertEquals("mock-token", Objects.requireNonNull(response.getCookie("JWT_TOKEN")).getValue(), "Cookie value should match the mocked token");
        assertEquals("User logged in successfully", result.getBody(), "Response body should confirm login success");
    }

    @Test
    void shouldRegisterUserSuccessfully() {
        UUID userId = UUID.randomUUID();
        Instant now = Instant.now();
        UserCreateRequestDTO requestDTO = new UserCreateRequestDTO(
                "Name Example",
                "Username Example",
                "email@example.com",
                "password");
        UserDetailsResponseDTO userDetailsResponseDTO = new UserDetailsResponseDTO(
                userId,
                "Name Example",
                "Username Example",
                "email@example.com",
                List.of(new OwnerOfDTO(UUID.randomUUID(), "Name Example", Instant.now())),
                List.of(new ParticipatesInDTO(UUID.randomUUID(), "Name Example", Instant.now())),
                0L,
                now,
                now);

        Cookie cookie = new Cookie("JWT_TOKEN", "mock-token");
        Map<String, Object> String = Map.of(
                "user", userDetailsResponseDTO,
                "cookie", cookie
        );

        when(signUpUseCase.execute(any(UserCreateRequestDTO.class))).thenReturn(String);

        MockHttpServletResponse response = new MockHttpServletResponse();

        ResponseEntity<UserDetailsResponseDTO> result = authController.signUp(requestDTO, response);

        assertEquals(200, result.getStatusCode().value(), "Response status should be 200 OK");
        assertTrue(response.containsHeader("Set-Cookie"), "Response should contain Set-Cookie header");
        assertEquals("mock-token", Objects.requireNonNull(response.getCookie("JWT_TOKEN")).getValue(),
                "Cookie value should match the mocked token");
        assertEquals(userDetailsResponseDTO, result.getBody(), "Response body should confirm registration success");
    }

    @Test
    void shouldLogoutUserSuccessfully() {
        Cookie logoutCookie = new Cookie("JWT_TOKEN", null);
        logoutCookie.setMaxAge(0);
        when(logoutUseCase.execute()).thenReturn(logoutCookie);

        MockHttpServletResponse response = new MockHttpServletResponse();

        ResponseEntity<Void> result = authController.logout(response);

        assertEquals(200, result.getStatusCode().value(), "Response status should be 200 OK");
        assertTrue(response.containsHeader("Set-Cookie"), "Response should contain Set-Cookie header for logout");
        assertNull(Objects.requireNonNull(response.getCookie("JWT_TOKEN")).getValue(), "Logout cookie value should be null");
    }
}
