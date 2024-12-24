package br.com.soupaulodev.forumhub.modules.user.usecase;

import br.com.soupaulodev.forumhub.modules.exception.usecase.UnauthorizedException;
import br.com.soupaulodev.forumhub.modules.user.entity.UserEntity;
import br.com.soupaulodev.forumhub.modules.user.entity.UserHighsEntity;
import br.com.soupaulodev.forumhub.modules.user.repository.UserHighsRepository;
import br.com.soupaulodev.forumhub.modules.user.repository.UserRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

/**
 * @author <a href="https://soupaulodev.com.br">soupaulodev</a>
 */
class HighUserUseCaseTest {

    @Mock
    private UserHighsRepository userHighsRepository;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private HighUserUseCase highUserUseCase;

    private final UUID highedUser = UUID.randomUUID();
    private final UUID authenticatedUserId = UUID.randomUUID();


    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @AfterEach
    void tearDown() {
        SecurityContextHolder.clearContext();
    }

    @Test
    void testExecute_shouldHighUserSuccessfully() {
        when(userHighsRepository.findByHighedUser_IdAndHighingUser_Id(highedUser, authenticatedUserId))
                .thenReturn(Optional.empty());
        when(userRepository.findById(highedUser)).thenReturn(Optional.of(new UserEntity()));
        when(userRepository.findById(authenticatedUserId)).thenReturn(Optional.of(new UserEntity()));
        when(userHighsRepository.save(new UserHighsEntity(new UserEntity(), new UserEntity()))).thenReturn(new UserHighsEntity());

        highUserUseCase.execute(highedUser, authenticatedUserId);

        verify(userHighsRepository, times(1)).findByHighedUser_IdAndHighingUser_Id(highedUser, authenticatedUserId);
    }

    @Test
    void testExecute_shouldThrowIllegalArgumentException_whenHighedUserIsEqualToAuthenticatedUserId() {
        when(userHighsRepository.findByHighedUser_IdAndHighingUser_Id(highedUser, authenticatedUserId))
                .thenReturn(Optional.empty());
        when(userRepository.findById(highedUser)).thenReturn(Optional.of(new UserEntity()));
        when(userRepository.findById(authenticatedUserId)).thenReturn(Optional.of(new UserEntity()));

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> highUserUseCase.execute(authenticatedUserId, authenticatedUserId));

        assertEquals("User cannot high himself", exception.getMessage());
    }

    @Test
    void testExecute_shouldThrowIllegalArgumentException_whenUserAlreadyHighed() {
        when(userHighsRepository.findByHighedUser_IdAndHighingUser_Id(highedUser, authenticatedUserId))
                .thenReturn(Optional.of(new UserHighsEntity()));

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> highUserUseCase.execute(highedUser, authenticatedUserId));

        assertEquals("User already highed", exception.getMessage());
    }

    @Test
    void testExecute_shouldThrowIllegalArgumentException_whenHighedUserNotFound() {
        when(userHighsRepository.findByHighedUser_IdAndHighingUser_Id(highedUser, authenticatedUserId))
                .thenReturn(Optional.empty());
        when(userRepository.findById(highedUser)).thenReturn(Optional.empty());

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> highUserUseCase.execute(highedUser, authenticatedUserId));

        assertEquals("User not found", exception.getMessage());
    }

    @Test
    void testExecute_shouldThrowU_whenAuthenticatedUserNotFound() {
        when(userHighsRepository.findByHighedUser_IdAndHighingUser_Id(highedUser, authenticatedUserId))
                .thenReturn(Optional.empty());
        when(userRepository.findById(highedUser)).thenReturn(Optional.of(new UserEntity()));
        when(userRepository.findById(authenticatedUserId)).thenReturn(Optional.empty());

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> highUserUseCase.execute(highedUser, authenticatedUserId));

        assertEquals("User not found", exception.getMessage());
    }

    @Test
    void shouldThrowExceptionWhenUserNotAuthenticated() {
        assertThrows(UnauthorizedException.class, () ->
                        highUserUseCase.execute(highedUser, null),
                "User not authenticated");
    }
}