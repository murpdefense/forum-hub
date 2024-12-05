package br.com.soupaulodev.forumhub.modules.user.controller.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;

/**
 * DTO (Data Transfer Object) representing the request body for user update.
 * <p>
 *     This class is used to capture the user's updated information, including their name, username, email, and password,
 *     when updating a user's profile. It uses Jakarta Bean Validation annotations to enforce that the input is validated
 *     before processing, ensuring that the user's information is updated correctly.
 * </p>
 * @param name the name of the user to be updated
 * @param username the username of the user to be updated
 * @param email the email of the user to be updated
 * @param password the password of the user to be updated
 * @author <a href="https://soupaulodev.com.br">soupaulodev</a>
 */
public record UserUpdateRequestDTO (@Size(max = 50)
                                    String name,

                                    @Size(max = 50)
                                    String username,

                                    @Email
                                    String email,

                                    @Size(min = 8, max = 50)
                                    String password) {}
