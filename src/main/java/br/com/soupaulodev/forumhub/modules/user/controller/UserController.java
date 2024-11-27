package br.com.soupaulodev.forumhub.modules.user.controller;

import br.com.soupaulodev.forumhub.modules.user.controller.dto.UserCreateRequestDTO;
import br.com.soupaulodev.forumhub.modules.user.controller.dto.UserResponseDTO;
import br.com.soupaulodev.forumhub.modules.user.controller.dto.UserUpdateRequestDTO;
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
    public ResponseEntity<UserResponseDTO> create(@Valid @RequestBody UserCreateRequestDTO requestDTO) {

        UserResponseDTO responseDTO = createUserUsecase.execute(requestDTO);
        URI uri = URI.create("/user/" + responseDTO.getId());
        return ResponseEntity.created(uri).body(responseDTO);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserResponseDTO> getUserById(@PathVariable("id") UUID id) {

        return ResponseEntity.ok(getUserUsecase.execute(id));
    }

    @GetMapping("/{nameOrUsername}")
    public ResponseEntity<UserResponseDTO> getUserByNameOrUsername(@PathVariable("nameOrUsername") String query) {

        return ResponseEntity.ok(findUserByNameOrUsernameUsecase.execute(query));
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserResponseDTO> updateUser(@PathVariable("id") UUID id,
                                      @Valid @RequestBody UserUpdateRequestDTO requestDTO) {

        return ResponseEntity.ok(updateUserUsecase.execute(id, requestDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable("id") UUID id) {

        deleteUserUsecase.execute(id);
        return ResponseEntity.noContent().build();
    }
}
