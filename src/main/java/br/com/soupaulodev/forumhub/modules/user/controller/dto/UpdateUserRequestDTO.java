package br.com.soupaulodev.forumhub.modules.user.controller.dto;

import jakarta.validation.constraints.Email;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

@Data
@AllArgsConstructor
public class UpdateUserRequestDTO {
    @Length(min = 3, max = 50)
    private String name;

    @Length(min = 3, max = 50)
    private String username;

    @Email(message = "Invalid email")
    private String email;

    @Length(min = 8, max = 50)
    private String password;
}
