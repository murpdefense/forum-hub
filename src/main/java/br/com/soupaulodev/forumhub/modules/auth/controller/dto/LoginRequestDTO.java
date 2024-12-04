package br.com.soupaulodev.forumhub.modules.auth.controller.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Length;


/**
 * DTO (Data Transfer Object) representing the request body for user login.
 * <p>
 * This class is used to capture the user's login credentials, including their username and password,
 * during the authentication process. It uses Jakarta Bean Validation annotations to enforce that
 * both fields are non-blank, ensuring that the input is validated before processing.
 * </p>
 *
 * @author soupaulodev
 */
public record LoginRequestDTO (

    /**
     * The username of the user attempting to log in.
     * This field must not be blank.
     */
    @NotBlank
    @NotNull
    String username,

    /**
     * The username of the user attempting to log in.
     * This field must not be blank.
     */
    @NotBlank
    @NotNull
    @Length(min = 8, max = 50)
    String password
) {}