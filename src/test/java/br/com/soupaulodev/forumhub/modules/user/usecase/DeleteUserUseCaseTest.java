package br.com.soupaulodev.forumhub.modules.user.usecase;

import br.com.soupaulodev.forumhub.modules.exception.usecase.ForbiddenException;
import br.com.soupaulodev.forumhub.modules.exception.usecase.ResourceNotFoundException;
import br.com.soupaulodev.forumhub.modules.user.entity.UserEntity;
import br.com.soupaulodev.forumhub.modules.user.repository.UserRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

/**
 * @author <a href="https://www.soupaulodev.com.br">soupaulodev</a>
 */
class DeleteUserUseCaseTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private DeleteUserUseCase deleteUserUseCase;

    private final UUID userId = UUID.randomUUID();
    private final UserEntity userEntity = new UserEntity(
            "John Doe",
            "john Doe",
            "johndoe@mail.com",
            "password");

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        userEntity.setId(UUID.randomUUID());
        SecurityContextHolder.getContext().setAuthentication(new UsernamePasswordAuthenticationToken(userId, null));
    }

    @AfterEach
    void tearDown() {
        SecurityContextHolder.clearContext();
    }

    @Test
    void testExecute_shouldDeleteUserSuccessfully() {
        userEntity.setId(userId);

        when(userRepository.findById(userId)).thenReturn(Optional.of(userEntity));

        deleteUserUseCase.execute(userId, userId);

        verify(userRepository, times(1)).delete(userEntity);
    }

    @Test
    void testExecute_shouldThrowResourceNotFoundException() {
        when(userRepository.findById(userId)).thenThrow(new ResourceNotFoundException("User not found."));

        assertThrows(ResourceNotFoundException.class, () -> deleteUserUseCase.execute(userId, userId));
    }

    @Test
    void testExecute_shouldThrowForbiddenException() {
        userEntity.setId(UUID.randomUUID());

        when(userRepository.findById(userId)).thenReturn(Optional.of(userEntity));

        assertThrows(ForbiddenException.class, () -> deleteUserUseCase.execute(userId, userId));
    }
}