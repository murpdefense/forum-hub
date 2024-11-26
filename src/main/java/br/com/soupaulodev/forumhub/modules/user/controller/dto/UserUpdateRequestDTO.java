package br.com.soupaulodev.forumhub.modules.user.controller.dto;

import br.com.soupaulodev.forumhub.modules.user.entity.UserRole;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserUpdateRequestDTO {

    @Size(max = 50)
    private String name;

    @Size(max = 50)
    private String username;

    @Email
    @Size(max = 50)
    private String email;

    @Size(min = 8, max = 50)
    private String password;

    private UserRole role;
}
