package br.com.soupaulodev.forumhub.modules.user.usecase;

import br.com.soupaulodev.forumhub.modules.exception.usecase.ResourceNotFoundException;
import br.com.soupaulodev.forumhub.modules.exception.usecase.UnauthorizedException;
import br.com.soupaulodev.forumhub.modules.user.entity.UserHighsEntity;
import br.com.soupaulodev.forumhub.modules.user.repository.UserHighsRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

/**
 * @author <a href="https://soupaulodev.com.br">soupaulodev</a>
 */
class UnHighUserUseCaseTest {

    @Mock
    private UserHighsRepository userHighsRepository;

    @InjectMocks
    private UnHighUserUseCase unHighUserUseCase;

    private final UUID unHighedUser = UUID.randomUUID();
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
    void testExecute_shouldUnHighUserSuccessfully() {
        UserHighsEntity userHighsEntity = mock(UserHighsEntity.class);
        when(userHighsRepository.findByHighedUser_IdAndHighingUser_Id(unHighedUser, authenticatedUserId))
                .thenReturn(Optional.of(userHighsEntity));

        unHighUserUseCase.execute(unHighedUser, authenticatedUserId);

        verify(userHighsRepository, times(1))
                .findByHighedUser_IdAndHighingUser_Id(unHighedUser, authenticatedUserId);
        verify(userHighsRepository, times(1)).delete(userHighsEntity);
        verifyNoMoreInteractions(userHighsRepository);
    }

    @Test
    void testExecute_shouldThrowExceptionWhenUserNotAuthenticated() {
        assertThrows(UnauthorizedException.class, () ->
                        unHighUserUseCase.execute(unHighedUser, null),
                "User not authenticated");

        verifyNoInteractions(userHighsRepository);
    }

    @Test
    void testExecute_shouldThrowExceptionWhenUserTriesToUnHighHimself() {
        assertThrows(IllegalArgumentException.class, () ->
                        unHighUserUseCase.execute(authenticatedUserId, authenticatedUserId),
                "User cannot unhigh himself");

        verifyNoInteractions(userHighsRepository);
    }

    @Test
    void testExecute_shouldThrowExceptionWhenUserNotHighed() {
        when(userHighsRepository.findByHighedUser_IdAndHighingUser_Id(unHighedUser, authenticatedUserId))
                .thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () ->
                        unHighUserUseCase.execute(unHighedUser, authenticatedUserId),
                "User not highed");

        verify(userHighsRepository, times(1))
                .findByHighedUser_IdAndHighingUser_Id(unHighedUser, authenticatedUserId);
        verifyNoMoreInteractions(userHighsRepository);
    }
}