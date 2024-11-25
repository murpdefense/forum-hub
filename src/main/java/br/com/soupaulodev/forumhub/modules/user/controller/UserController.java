package br.com.soupaulodev.forumhub.modules.user.controller;

import br.com.soupaulodev.forumhub.modules.user.controller.dto.CreateUserRequestDTO;
import br.com.soupaulodev.forumhub.modules.user.controller.dto.UserResponseDTO;
import br.com.soupaulodev.forumhub.modules.user.entity.UserEntity;
import br.com.soupaulodev.forumhub.modules.user.mapper.UserMapper;
import br.com.soupaulodev.forumhub.modules.user.usecase.*;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.UUID;

@RestController
@RequestMapping("/user")
public class UserController {
    private final CreateUserUsecase createUserUsecase;
    private final GetUserUsecase getUserUsecase;
    private final FindUserByNameOrUsernameUsecase findUserByNameOrUsernameUsecase;
    private final UpdateUserUsecase updateUserUsecase;
    private final DeleteUserUsecase deleteUserUsecase;

    public UserController(CreateUserUsecase createUserUsecase,
                          GetUserUsecase getUserUsecase,
                          FindUserByNameOrUsernameUsecase findUserByNameOrUsernameUsecase,
                          UpdateUserUsecase updateUserUsecase,
                          DeleteUserUsecase deleteUserUsecase) {
        this.createUserUsecase = createUserUsecase;
        this.getUserUsecase = getUserUsecase;
        this.findUserByNameOrUsernameUsecase = findUserByNameOrUsernameUsecase;
        this.updateUserUsecase = updateUserUsecase;
        this.deleteUserUsecase = deleteUserUsecase;
    }

    @PostMapping
    public ResponseEntity<UserResponseDTO> create(@Valid @RequestBody CreateUserRequestDTO requestDTO) {
        UserEntity userCreated = createUserUsecase.execute(UserMapper.toEntity(requestDTO));
        URI uri = URI.create("/user/" + userCreated.getId());
        return ResponseEntity.created(uri).body(UserMapper.toResponseDTO(userCreated));
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserResponseDTO> getUserById(@PathVariable("id") UUID id) {
        UserEntity user = getUserUsecase.execute(id);
        return ResponseEntity.ok(UserMapper.toResponseDTO(user));
    }

    @GetMapping("/{nameOrUsername}")
    public ResponseEntity<UserResponseDTO> getUserByNameOrUsername(@PathVariable("nameOrUsername") String query) {
        UserEntity user = findUserByNameOrUsernameUsecase.execute(query);
        return ResponseEntity.ok(UserMapper.toResponseDTO(user));
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserResponseDTO> updateUser(@PathVariable("id") UUID id,
                                      @Valid @RequestBody UpdateUserUsecase requestDTO) {
        UserEntity userUpdated = updateUserUsecase.execute(id, UserMapper.toEntity(requestDTO));
        return ResponseEntity.ok(UserMapper.toResponseDTO(userUpdated));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable("id") UUID id) {
        deleteUserUsecase.execute(id);
        return ResponseEntity.noContent().build();
    }
}
