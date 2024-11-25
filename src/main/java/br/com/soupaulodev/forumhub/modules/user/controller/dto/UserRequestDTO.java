package br.com.soupaulodev.forumhub.modules.user.controller.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

@Data
@AllArgsConstructor
public class UserRequestDTO {

    @NotBlank
    @Length(min = 3, max = 50, message = "Name must be between 3 and 50 characters")
    private String name;

    @NotBlank
    @Length(min = 3, max = 50, message = "Name must be between 3 and 50 characters")
    private String username;

    @NotBlank
    @Email(message = "Invalid email")
    private String email;

    @NotBlank
    @Length(min = 8, max = 50, message = "Name must be between 3 and 50 characters")
    private String password;
}
