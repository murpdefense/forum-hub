package br.com.soupaulodev.forumhub.modules.user.controller.dto;

import br.com.soupaulodev.forumhub.modules.user.entity.UserRole;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * The role of the user.
 */
@Data
@AllArgsConstructor
public class UserUpdateRequestDTO {

    /**
     * The name of the user.
     * Must have a maximum size of 50 characters.
     */
    @Size(max = 50)
    private String name;

    /**
     * The username of the user.
     * Must have a maximum size of 50 characters.
     */
    @Size(max = 50)
    private String username;

    /**
     * The email of the user.
     * Must be a valid email format and have a maximum size of 50 characters.
     */
    @Email
    @Size(max = 50)
    private String email;

    /**
     * The password of the user.
     * Must have a size between 8 and 50 characters.
     */
    @Size(min = 8, max = 50)
    private String password;

    /**
     * The role of the user.
     */
    private UserRole role;
}
