package br.com.soupaulodev.forumhub.modules.user.controller.dto;

import br.com.soupaulodev.forumhub.modules.user.entity.UserRole;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * Data Transfer Object for creating a new user.
 */
@Data
@AllArgsConstructor
public class UserCreateRequestDTO {

    /**
     * The name of the user.
     * Must not be blank and have a maximum size of 50 characters.
     */
    @NotBlank
    @Size(max = 50)
    private String name;

    /**
     * The username of the user.
     * Must not be blank and have a maximum size of 50 characters.
     */
    @NotBlank
    @Size(max = 50)
    private String username;

    /**
     * The email of the user.
     * Must not be blank, must be a valid email format, and have a maximum size of 50 characters.
     */
    @NotBlank
    @Email
    @Size(max = 50)
    private String email;

    /**
     * The password of the user.
     * Must not be blank and must have a size between 8 and 50 characters.
     */
    @NotBlank
    @Size(min = 8, max = 50)
    private String password;

    /**
     * The role of the user.
     * Must not be null.
     */
    @NotNull
    private UserRole role;
}
