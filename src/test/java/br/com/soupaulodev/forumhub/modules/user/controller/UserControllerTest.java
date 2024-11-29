package br.com.soupaulodev.forumhub.modules.user.controller;

import br.com.soupaulodev.forumhub.modules.exception.usecase.UserAlreadyExistsException;
import br.com.soupaulodev.forumhub.modules.exception.usecase.UserNotFoundException;
import br.com.soupaulodev.forumhub.modules.user.controller.dto.UserCreateRequestDTO;
import br.com.soupaulodev.forumhub.modules.user.controller.dto.UserResponseDTO;
import br.com.soupaulodev.forumhub.modules.user.controller.dto.UserUpdateRequestDTO;
import br.com.soupaulodev.forumhub.modules.user.entity.UserRole;
import br.com.soupaulodev.forumhub.modules.user.usecase.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import java.net.URI;
import java.time.Instant;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class UserControllerTest {

    @Mock
    private CreateUserUsecase createUserUsecase;

    @Mock
    private GetUserUsecase getUserUsecase;

    @Mock
    private FindUserByNameOrUsernameUsecase findUserByNameOrUsernameUsecase;

    @Mock
    private UpdateUserUsecase updateUserUsecase;

    @Mock
    private DeleteUserUsecase deleteUserUsecase;

    @InjectMocks
    private UserController userController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void shouldCreateUserSuccessfully() {

        UUID userId = UUID.randomUUID();
        Instant now = Instant.now();
        UserCreateRequestDTO requestDTO = new UserCreateRequestDTO(
                "Name Example",
                "Username Example",
                "email@example.com",
                "password",
                UserRole.USER);
        UserResponseDTO responseDTO = new UserResponseDTO(
                userId,
                "Name Example",
                "Username Example",
                "email@example.com",
                UserRole.USER,
                now,
                now);

        when(createUserUsecase.execute(any(UserCreateRequestDTO.class))).thenReturn(responseDTO);

        ResponseEntity<UserResponseDTO> response = userController.create(requestDTO);

        assertEquals(201, response.getStatusCodeValue());
        assertEquals(responseDTO, response.getBody());
        assertEquals(URI.create("/user/" + responseDTO.getId()), response.getHeaders().getLocation());
    }

    @Test
    void shouldThrowAlreadyExistsExceptionWhenCreatingUserWithExistingUsername() {

        UserCreateRequestDTO requestDTO = new UserCreateRequestDTO(
                "Name Example",
                "Username Example",
                "email@example.com",
                "password",
                UserRole.USER);

        when(createUserUsecase.execute(any(UserCreateRequestDTO.class))).thenThrow(new UserAlreadyExistsException());

        assertThrows(UserAlreadyExistsException.class, () -> userController.create(requestDTO));
    }

    @Test
    void shouldGetUserByIdSuccessfully() {

        UUID userId = UUID.randomUUID();
        Instant now = Instant.now();
        UserResponseDTO responseDTO = new UserResponseDTO(
                userId,
                "Name Example",
                "Username Example",
                "email@example.com",
                UserRole.USER,
                now,
                now);

        when(getUserUsecase.execute(userId)).thenReturn(responseDTO);

        ResponseEntity<UserResponseDTO> response = userController.getUserById(userId);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(responseDTO, response.getBody());
    }

    @Test void shouldThrowUserNotFoundExceptionWhenGettingUserById() {

        UUID userId = UUID.randomUUID();

        when(getUserUsecase.execute(userId)).thenThrow(new UserNotFoundException());

        assertThrows(UserNotFoundException.class, () -> userController.getUserById(userId));
    }

    @Test
    void shouldGetUserByNameOrUsernameSuccessfully() {

        UUID userId = UUID.randomUUID();
        Instant now = Instant.now();
        String query = "username";
        UserResponseDTO responseDTO = new UserResponseDTO(
                userId,
                "Name Example",
                "Username Example",
                "email@example.com",
                UserRole.USER,
                now,
                now);

        when(findUserByNameOrUsernameUsecase.execute(query)).thenReturn(responseDTO);

        ResponseEntity<UserResponseDTO> response = userController.getUserByNameOrUsername(query);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(responseDTO, response.getBody());
    }

    @Test
    void shouldThrowUserNotFoundExceptionWhenGettingUserByNameOrUsername() {

        String query = "username";

        when(findUserByNameOrUsernameUsecase.execute(query)).thenThrow(new UserNotFoundException());

        assertThrows(UserNotFoundException.class, () -> userController.getUserByNameOrUsername(query));
    }

    @Test
    void shouldUpdateUserSuccessfully() {

        UUID userId = UUID.randomUUID();
        Instant now = Instant.now();
        UserUpdateRequestDTO requestDTO = new UserUpdateRequestDTO(
                "Name Example",
                "Username Example",
                "email@example.com",
                "password",
                UserRole.USER);
        UserResponseDTO responseDTO = new UserResponseDTO(
                userId,
                "Name Example",
                "Username Example",
                "email@example.com",
                UserRole.USER,
                now,
                now);

        when(updateUserUsecase.execute(userId, requestDTO)).thenReturn(responseDTO);

        ResponseEntity<UserResponseDTO> response = userController.updateUser(userId, requestDTO);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(responseDTO, response.getBody());
    }

    @Test
    void shouldThrowUserNotFoundExceptionWhenUpdatingUser() {

        UUID userId = UUID.randomUUID();
        UserUpdateRequestDTO requestDTO = new UserUpdateRequestDTO(
                "Name Example",
                "Username Example",
                "email@example.com",
                "password",
                UserRole.USER);

        when(updateUserUsecase.execute(userId, requestDTO)).thenThrow(new UserNotFoundException());

        assertThrows(UserNotFoundException.class, () -> userController.updateUser(userId, requestDTO));
    }

    @Test
    void shouldDeleteUserSuccessfully() {
        UUID userId = UUID.randomUUID();

        doNothing().when(deleteUserUsecase).execute(userId);

        ResponseEntity<Void> response = userController.deleteUser(userId);

        assertEquals(204, response.getStatusCodeValue());
        verify(deleteUserUsecase, times(1)).execute(userId);
    }

    @Test
    void shouldThrowUserNotFoundExceptionWhenDeletingUser() {

        UUID userId = UUID.randomUUID();

        doThrow(new UserNotFoundException()).when(deleteUserUsecase).execute(userId);

        assertThrows(UserNotFoundException.class, () -> userController.deleteUser(userId));
    }
}