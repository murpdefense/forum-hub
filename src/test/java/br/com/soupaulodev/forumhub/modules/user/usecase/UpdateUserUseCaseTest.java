package br.com.soupaulodev.forumhub.modules.user.usecase;

import br.com.soupaulodev.forumhub.modules.exception.usecase.ForbiddenException;
import br.com.soupaulodev.forumhub.modules.exception.usecase.ResourceNotFoundException;
import br.com.soupaulodev.forumhub.modules.exception.usecase.UnauthorizedException;
import br.com.soupaulodev.forumhub.modules.user.controller.dto.UserResponseDTO;
import br.com.soupaulodev.forumhub.modules.user.controller.dto.UserUpdateRequestDTO;
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
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

/**
 * @author <a href="https://soupaulodev.com.br">soupaulodev</a>
 */
class UpdateUserUseCaseTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UpdateUserUseCase updateUserUseCase;

    private UserEntity userEntity;
    private UUID userId;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        userEntity = new UserEntity(
                "John Doe",
                "johndoe",
                "johndoe@mail.com",
                "passwordEncrypted");
        userId = UUID.randomUUID();
        SecurityContextHolder.getContext().setAuthentication(new UsernamePasswordAuthenticationToken(userId, null));
    }

    @AfterEach
    void tearDown() {
        SecurityContextHolder.clearContext();
    }

    @Test
    void testExecute_shouldUpdateUserSucessfully() {
        userEntity.setId(userId);
        UserUpdateRequestDTO requestDTO = new UserUpdateRequestDTO(
                "John Doe Updated",
                "johnDoeUpdated",
                "johndoeupdated@mail.com",
                "newPasswordNoEncrypted");

        when(userRepository.findById(userId)).thenReturn(Optional.of(userEntity));
        when(userRepository.save(userEntity)).thenReturn(userEntity);
        when(passwordEncoder.encode(requestDTO.password())).thenReturn("newPasswordEncrypted");

        SecurityContextHolder.getContext().setAuthentication(new UsernamePasswordAuthenticationToken(userId, null));

        UserResponseDTO result = updateUserUseCase.execute(userId, requestDTO, userId);
        UserResponseDTO expectedResponse = new UserResponseDTO(
                userId,
                "John Doe Updated",
                "johnDoeUpdated",
                0L,
                userEntity.getCreatedAt(),
                result.updatedAt());

        assertThat(result).isEqualTo(expectedResponse);
    }

    @Test
    void testExecute_shouldThrowResourceNotFoundException() {
        UserUpdateRequestDTO requestDTO = new UserUpdateRequestDTO(
                "John Doe Updated",
                "johnDoeUpdated",
                "johndoe@mail.com",
                "newPasswordNoEncrypted");

        when(userRepository.findById(any(UUID.class))).thenThrow(new ResourceNotFoundException("User not found."));

        assertThrows(ResourceNotFoundException.class, () -> updateUserUseCase.execute(userId, requestDTO, userId));
    }

    @Test
    void testExecute_shouldThrowForbiddenException() {
        UserUpdateRequestDTO requestDTO = new UserUpdateRequestDTO(
                "John Doe Updated",
                "johnDoeUpdated",
                "johndoe@mail.com",
                "newPasswordNoEncrypted");

        UUID authenticatedUserId = UUID.randomUUID();

        when(userRepository.findById(userId)).thenReturn(Optional.of(userEntity));

        assertThrows(ForbiddenException.class, () -> updateUserUseCase.execute(userId, requestDTO, authenticatedUserId));
    }

    @Test
    void testExecute_shouldThrowIllegalArgumentException() {
        UserUpdateRequestDTO requestDTO = new UserUpdateRequestDTO(null, null, null, null);

        when(userRepository.findById(userId)).thenThrow(new IllegalArgumentException("""
                You must provide at least one field to update:
                - name
                - username
                - email
                - password
                """));

        assertThrows(IllegalArgumentException.class, () -> updateUserUseCase.execute(userId, requestDTO, userId));
    }

    @Test
    void testExecute_shouldThrowUnauthorizedException() {
        UUID userId = UUID.randomUUID();
        UserUpdateRequestDTO requestDTO = new UserUpdateRequestDTO(
                "John Doe Updated",
                null,
                null,
                null);

        when(userRepository.findById(userId)).thenThrow(new UnauthorizedException("You are not allowed to update this user."));

        assertThrows(UnauthorizedException.class, () -> updateUserUseCase.execute(userId, requestDTO, eq(UUID.randomUUID())));
    }
}