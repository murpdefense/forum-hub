package br.com.soupaulodev.forumhub.modules.auth.usecase;

import br.com.soupaulodev.forumhub.modules.auth.controller.dto.LoginRequestDTO;
import br.com.soupaulodev.forumhub.modules.user.entity.UserEntity;
import br.com.soupaulodev.forumhub.modules.user.repository.UserRepository;
import br.com.soupaulodev.forumhub.security.utils.CookieUtil;
import jakarta.servlet.http.Cookie;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

/**
 * Test class for the LoginUseCase class.
 * <p>
 * This class contains the test methods for the LoginUseCase class.
 * It tests the behavior of the use case when authenticating a user.
 * </p>
 *
 * @author <a href="https://soupaulodev.com.br">soupaulodev</a>
 */
class LoginUseCaseTest {

    @Mock
    private AuthenticationManager authenticationManager;

    @Mock
    private UserRepository userRepository;

    @Mock
    private CookieUtil cookieUtil;

    @InjectMocks
    private LoginUseCase loginUseCase;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void execute_ValidCredentials_ReturnsCookie() {
        LoginRequestDTO requestDTO = new LoginRequestDTO("validUser", "validPassword");
        UserEntity userEntity = new UserEntity();
        userEntity.setUsername("validUser");
        Cookie mockCookie = new Cookie("auth", "mockToken");

        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
                .thenReturn(null);
        when(userRepository.findByUsername("validUser")).thenReturn(Optional.of(userEntity));
        when(cookieUtil.generateCookieWithToken(userEntity)).thenReturn(mockCookie);

        Cookie result = loginUseCase.execute(requestDTO);

        assertNotNull(result);
        assertEquals("auth", result.getName());
        assertEquals("mockToken", result.getValue());
        verify(authenticationManager).authenticate(any(UsernamePasswordAuthenticationToken.class));
        verify(userRepository).findByUsername("validUser");
        verify(cookieUtil).generateCookieWithToken(userEntity);
    }

    @Test
    void execute_InvalidCredentials_ThrowsBadCredentialsException() {
        LoginRequestDTO requestDTO = new LoginRequestDTO("invalidUser", "invalidPassword");

        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
                .thenThrow(new BadCredentialsException("Invalid username or password"));

        BadCredentialsException exception = assertThrows(
                BadCredentialsException.class,
                () -> loginUseCase.execute(requestDTO)
        );
        assertEquals("Invalid username or password", exception.getMessage());
        verify(authenticationManager).authenticate(any(UsernamePasswordAuthenticationToken.class));
        verify(userRepository, never()).findByUsername(anyString());
        verify(cookieUtil, never()).generateCookieWithToken(any());
    }

    @Test
    void execute_UserNotFound_ThrowsUsernameNotFoundException() {
        LoginRequestDTO requestDTO = new LoginRequestDTO("nonExistentUser", "password");

        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
                .thenReturn(null);
        when(userRepository.findByUsername("nonExistentUser")).thenReturn(Optional.empty());

        UsernameNotFoundException exception = assertThrows(
                UsernameNotFoundException.class,
                () -> loginUseCase.execute(requestDTO)
        );
        assertEquals("User not found", exception.getMessage());
        verify(authenticationManager).authenticate(any(UsernamePasswordAuthenticationToken.class));
        verify(userRepository).findByUsername("nonExistentUser");
        verify(cookieUtil, never()).generateCookieWithToken(any());
    }

    @Test
    void execute_AuthenticationException_ThrowsBadCredentialsException() {
        LoginRequestDTO requestDTO = new LoginRequestDTO("user", "password");

        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
                .thenThrow(new AuthenticationException("Authentication failed") {});

        BadCredentialsException exception = assertThrows(
                BadCredentialsException.class,
                () -> loginUseCase.execute(requestDTO)
        );
        assertEquals("Invalid username or password", exception.getMessage());
        verify(authenticationManager).authenticate(any(UsernamePasswordAuthenticationToken.class));
        verify(userRepository, never()).findByUsername(anyString());
        verify(cookieUtil, never()).generateCookieWithToken(any());
    }
}
