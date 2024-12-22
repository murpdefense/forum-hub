package br.com.soupaulodev.forumhub.modules.auth.usecase;

import br.com.soupaulodev.forumhub.modules.exception.usecase.ResourceAlreadyExistsException;
import br.com.soupaulodev.forumhub.modules.user.controller.dto.UserCreateRequestDTO;
import br.com.soupaulodev.forumhub.modules.user.entity.UserEntity;
import br.com.soupaulodev.forumhub.modules.user.repository.UserRepository;
import br.com.soupaulodev.forumhub.security.utils.CookieUtil;
import jakarta.servlet.http.Cookie;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * Test class for the SignUpUseCase class.
 * <p>
 * This class contains the test methods for the SignUpUseCase class.
 * It tests the behavior of the use case when registering a new user.
 * </p>
 *
 * @author <a href="https://soupaulodev.com.br">soupaulodev</a>
 */
class SignUpUseCaseTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private CookieUtil cookieUtil;

    @InjectMocks
    private SignUpUseCase signUpUseCase;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void shouldRegisterUserSuccessfully() {
        UserCreateRequestDTO requestDTO = new UserCreateRequestDTO("testuser", "password123", "Test User", "test@example.com");

        when(userRepository.existsByUsername(requestDTO.username())).thenReturn(false);
        when(passwordEncoder.encode(requestDTO.password())).thenReturn("encodedPassword");
        when(cookieUtil.generateCookieWithToken(any(UserEntity.class))).thenReturn(new Cookie("token", "jwtToken"));

        UserEntity savedUser = new UserEntity();
        savedUser.setUsername(requestDTO.username());
        savedUser.setPassword("encodedPassword");
        savedUser.setName(requestDTO.name());
        savedUser.setEmail(requestDTO.email());

        Map<String, Object> result = signUpUseCase.execute(requestDTO);

        assertNotNull(result);
        assertTrue(result.containsKey("user"));
        assertTrue(result.containsKey("cookie"));

        verify(userRepository, times(1)).save(any(UserEntity.class));
        verify(cookieUtil, times(1)).generateCookieWithToken(any(UserEntity.class));
    }

    @Test
    void shouldThrowExceptionWhenUsernameAlreadyExists() {
        UserCreateRequestDTO requestDTO = new UserCreateRequestDTO("testuser", "password123", "Test User", "test@example.com");

        when(userRepository.existsByUsername(requestDTO.username())).thenReturn(true);

        ResourceAlreadyExistsException exception = assertThrows(ResourceAlreadyExistsException.class, () ->
                signUpUseCase.execute(requestDTO)
        );

        assertEquals("Username already exists", exception.getMessage());

        verify(userRepository, never()).save(any(UserEntity.class));
        verify(cookieUtil, never()).generateCookieWithToken(any(UserEntity.class));
    }
}
