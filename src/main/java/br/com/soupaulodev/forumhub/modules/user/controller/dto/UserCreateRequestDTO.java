package br.com.soupaulodev.forumhub.modules.user.controller.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

/**
 * DTO (Data Transfer Object) representing the request body for user creation.
 * <p>
 * This class is used to capture the user's information, including their name, username, email, and password,
 * when creating a new user. It uses Jakarta Bean Validation annotations to enforce that the input is validated
 * before processing, ensuring that the user is created correctly.
 * </p>
 *
 * @param name     the name of the user
 * @param username the username of the user
 * @param email    the email of the user
 * @param password the password of the user
 * @author <a href="https://soupaulodev.com.br">soupaulodev</a>
 */
public record UserCreateRequestDTO(@NotBlank
                                   @Size(max = 50)
                                   String name,

                                   @NotBlank
                                   @Size(max = 50)
                                   String username,

                                   @NotBlank
                                   @Email
                                   String email,

                                   @NotBlank
                                   @Size(min = 8, max = 50)
                                   String password) {
}